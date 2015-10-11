package com.mastercard.sc.dashboard.service.dashboard;

import com.mastercard.sc.dashboard.domain.*;
import com.mastercard.sc.dashboard.domain.Currency;
import com.mastercard.sc.dashboard.domain.client.*;
import com.mastercard.sc.dashboard.service.generator.PaymentInfoGenerator;
import com.mastercard.sc.dashboard.service.payment.PaymentService;
import com.mastercard.sc.dashboard.util.PriceConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DashboardServiceImpl implements DashboardService {

    private static final Logger LOGGER = LoggerFactory.getLogger(DashboardServiceImpl.class);

    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    private Random random = new Random();

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private PaymentInfoGenerator paymentInfoGenerator;

    @Autowired
    private PaymentService paymentService;

    private List<FraudAttempt> fraudAttemptList = new ArrayList<>();

    private TotalCounts totalCounts = new TotalCounts();

    private Map<String, Transaction> paymentTransactions = new HashMap<>();

    private Map<String, Transaction> refundTransactions = new HashMap<>();

    private ClientRevenue clientRevenue = new ClientRevenue();

    private CurrencyRevenue currencyRevenue = new CurrencyRevenue();

    private Map<String, ItemPaymentRefund> itemPaymentRefundMap = new HashMap<>();

    @Override
    public void updateClients() {
        messagingTemplate.convertAndSend("/topic/totalCounts", totalCounts);
        messagingTemplate.convertAndSend("/topic/frauds", fraudAttemptList);
        messagingTemplate.convertAndSend("/topic/paymentTransactions", paymentTransactions.values());
        messagingTemplate.convertAndSend("/topic/refundTransactions", refundTransactions.values());
        messagingTemplate.convertAndSend("/topic/currencyRevenue", currencyRevenue);
        messagingTemplate.convertAndSend("/topic/clientRevenue", clientRevenue);
        messagingTemplate.convertAndSend("/topic/itemPaymentRefund", itemPaymentRefundMap.values());
    }

    @Scheduled(fixedDelay = 2000)
    public void doPayment() {
        PaymentInfo paymentInfo = paymentInfoGenerator.generate();
        String creditCardNumber = paymentInfo.getUser().getCreditCard().getNumber();
        try {
            // check for fraud
            Account account = paymentService.getLostStolen(paymentInfo.getUser().getCreditCard().getNumber());
            if (account.isListed()) {
                LOGGER.info("Fraud attempt detected: " + account);
                updateForFraud(account, paymentInfo);
                return;
            }
        } catch (Exception e) {
            LOGGER.info("Credit Card is valid: " + creditCardNumber);
            // it's a valid credit card but not listed (API sends exception in this case)
            // continue to payment
        }

        PaymentResult paymentResult = paymentService.payment(paymentInfo);
        if (paymentResult != null) {
            updateForPayment(paymentResult);
        }
    }

    @Scheduled(fixedDelay = 15000)
    public void doRefund() {
        RefundInfo refundInfo = getRefundInfo();
        if (refundInfo != null) {
            if (paymentService.refund(refundInfo)) {
                updateForRefund(refundInfo);
            }
        }
    }

    private void updateForPayment(PaymentResult paymentResult) {
        PaymentInfo paymentInfo = paymentResult.getPaymentInfo();
        Item item = paymentInfo.getItem();
        User user = paymentInfo.getUser();

        double price = paymentInfo.getQuantity() * item.getPrice().getAmount();

        paymentTransactions.put(paymentResult.getPaymentId(), createTransaction(paymentResult));
        totalCounts.updateTotalForPayment(paymentInfo.getQuantity(), item.getPrice().getAmount());
        clientRevenue.updateRevenueForPayment(user.getClient().getName(), price);
        currencyRevenue.updateRevenueForPayment(user.getCurrency().toString(), price);

        ItemPaymentRefund itemPaymentRefund = itemPaymentRefundMap.getOrDefault(item.getName(),
                new ItemPaymentRefund(item.getName()));
        itemPaymentRefund.updateRevenueForPayment(paymentInfo.getQuantity());
        itemPaymentRefundMap.put(item.getName(), itemPaymentRefund);

        updateClients();
    }

    private void updateForRefund(RefundInfo refundInfo) {
        String paymentId = refundInfo.getPaymentId();
        Transaction transaction = paymentTransactions.get(paymentId);
        transaction.setPaymentStatus("REFUNDED");

        Double amount = Double.valueOf(transaction.getAmount());
        int quantity = Integer.valueOf(transaction.getQuantity());

        refundTransactions.put(paymentId, transaction);
        totalCounts.updateTotalForRefund(quantity, amount);
        clientRevenue.updateRevenueForRefund(transaction.getClient(), amount * quantity);
        currencyRevenue.updateRevenueForRefund(transaction.getCurrency(), amount * quantity);

        ItemPaymentRefund itemPaymentRefund = itemPaymentRefundMap.getOrDefault(transaction.getItemName(),
                new ItemPaymentRefund(transaction.getItemName()));
        itemPaymentRefund.updateRevenueForRefund(Integer.valueOf(transaction.getQuantity()));
        itemPaymentRefundMap.put(transaction.getItemName(), itemPaymentRefund);

        // payment refunded, remove it from payment transactions
        paymentTransactions.remove(paymentId);

        updateClients();
    }

    private void updateForFraud(Account account, PaymentInfo paymentInfo) {
        User user = paymentInfo.getUser();
        Item item = paymentInfo.getItem();
        Currency currency = user.getCurrency();

        FraudAttempt fraudAttempt = new FraudAttempt("FRAUD DETECTED", user.getCreditCard().getNumber(),
                account.getReason(), sdf.format(new Date()), item.getName(),
                PriceConverter.getPrice(item.getPrice(), currency).getAmount() + " " + currency.toString(),
                String.valueOf(paymentInfo.getQuantity()));

        fraudAttemptList.add(fraudAttempt);

        // send client messages
        messagingTemplate.convertAndSend("/topic/fraud", fraudAttempt);
        messagingTemplate.convertAndSend("/topic/frauds", fraudAttemptList);
    }

    private Transaction createTransaction(PaymentResult paymentResult) {
        PaymentInfo paymentInfo = paymentResult.getPaymentInfo();
        Item item = paymentInfo.getItem();
        Currency currency = paymentInfo.getUser().getCurrency();

        return new Transaction(paymentResult.getPaymentId(),
                sdf.format(paymentResult.getPaymentDate()), item.getName(),
                item.getPrice().getAmount().toString(),
                String.valueOf(paymentInfo.getQuantity()), currency.toString(),
                paymentInfo.getUser().getClient().getName(), paymentResult.getPaymentStatus());
    }

    private RefundInfo getRefundInfo() {
        List<Transaction> transactions = new ArrayList<>(paymentTransactions.values());
        if (!transactions.isEmpty()) {
            Transaction transaction = transactions.get(random.nextInt(transactions.size()));
            String amount = String.valueOf(Double.valueOf(transaction.getAmount())
                    * Integer.valueOf(transaction.getQuantity()));
            return new RefundInfo(transaction.getPaymentId(), amount, transaction.getItemName() + " refunded");
        }

        return null;
    }
}
