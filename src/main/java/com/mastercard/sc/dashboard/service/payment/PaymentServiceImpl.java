package com.mastercard.sc.dashboard.service.payment;

import com.mastercard.sc.dashboard.domain.*;
import com.mastercard.sc.dashboard.util.PriceConverter;
import com.simplify.payments.PaymentsApi;
import com.simplify.payments.PaymentsMap;
import com.simplify.payments.domain.Payment;
import com.simplify.payments.domain.Refund;
import com.simplify.payments.exception.ApiException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@Service
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    private final String URL = "http://dmartin.org/fraud/loststolen/v1/account-inquiry?Format=XML";

    private final String APPROVED_STATUS = "APPROVED";

    @Autowired
    private RestTemplate restTemplate;

    @Value("${mc.simplify.publicKey}")
    private String publicKey;

    @Value("${mc.simplify.privateKey}")
    private String privateKey;

    @PostConstruct
    void initService() {
        PaymentsApi.PUBLIC_KEY = publicKey;
        PaymentsApi.PRIVATE_KEY = privateKey;
    }

    @Override
    public PaymentResult payment(PaymentInfo paymentInfo) {
        User user = paymentInfo.getUser();
        CreditCard creditCard = user.getCreditCard();
        Item item = paymentInfo.getItem();
        Price price = item.getPrice();

        try {
            Payment payment = Payment.create(new PaymentsMap()
                    .set("card.number", creditCard.getNumber())
                    .set("card.cvc", creditCard.getCvc())
                    .set("card.expMonth", creditCard.getExpMonth())
                    .set("card.expYear", creditCard.getExpYear())
                    .set("currency", user.getCurrency().toString())
                    .set("amount", PriceConverter.getPrice(price, user.getCurrency()).getAmount()
                            * paymentInfo.getQuantity())
                    .set("description", item.getName()));

            String paymentStatus = (String) payment.get("paymentStatus");
            if (APPROVED_STATUS.equals(paymentStatus)) {
                LOGGER.info("Payment approved: " + paymentInfo);
                String paymentId = (String) payment.get("id");
                long paymentDate = (long) payment.get("paymentDate");
                PaymentResult paymentResult = new PaymentResult(paymentId, paymentDate,
                        paymentStatus, paymentInfo);
                return paymentResult;
            }
        } catch (ApiException e) {
            LOGGER.error("Message: " + e.getMessage());
        }

        return null;
    }

    @Override
    public boolean refund(RefundInfo refundInfo) {
        try {
            Refund refund = Refund.create(new PaymentsMap()
                    .set("payment", refundInfo.getPaymentId())
                    .set("amount", refundInfo.getAmount())
                    .set("reason", refundInfo.getReason()));

            String refundStatus = (String) refund.get("paymentStatus");
            if (APPROVED_STATUS.equals(refundStatus)) {
                LOGGER.info("Payment refunded: " + refundInfo);
                return true;
            }
        } catch (ApiException e) {
            LOGGER.debug("Message: " + e.getMessage());
        }

        return false;
    }

    @Override
    public Account getLostStolen(String accountNumber) {
        AccountInquiry accountInquiry = new AccountInquiry();
        accountInquiry.setAccountNumber(accountNumber);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_XML);

        HttpEntity entity = new HttpEntity(accountInquiry, headers);
        ResponseEntity<Account> account = restTemplate.exchange(URL, HttpMethod.PUT, entity, Account.class);
        return account.getBody();
    }
}
