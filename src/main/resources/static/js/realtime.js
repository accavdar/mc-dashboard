var currencyRevenueChart = null;
var clientRevenueChart = null;
var itemPaymentRefundChart = null;

function connect() {
    socket = new SockJS('/dashboard');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/totalCounts', function (message) {
            updateTotalCounts(message);
        });
        stompClient.subscribe('/topic/fraud', function (message) {
            updateFraud(message);
        });
        stompClient.subscribe('/topic/currencyRevenue', function (message) {
            updateCurrencyRevenue(message);
        });
        stompClient.subscribe('/topic/clientRevenue', function (message) {
            updateClientRevenue(message);
        });
        stompClient.subscribe('/topic/itemPaymentRefund', function (message) {
            updateItemPaymentRefund(message);
        });

        $.get("/initializeData");
    });
}

function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
}

function updateTotalCounts(message) {
    var totalCounts = $.parseJSON(message.body);
    $('#total-revenue').html("$ " + totalCounts.totalRevenue);
    $('#total-refund').html("$ " + totalCounts.totalRefund);
    $('#total-purchased-items').html(totalCounts.totalPurchasedItems);
    $('#total-refunded-items').html(totalCounts.totalRefundedItems);
}

function updateFraud(message) {
    var fraud = $.parseJSON(message.body);
    console.log(fraud);
    new TabbedNotification({
        title: fraud.title,
        text: 'Account Number: ' + fraud.accountNumber + '\nReason: ' + fraud.fraudReason,
        type: 'error',
        sound: true
    });
}

function updateCurrencyRevenue(message) {
    currencyRevenueChart.load({
        json: $.parseJSON(message.body)
    });
}

function updateClientRevenue(message) {
    clientRevenueChart.load({
        json: $.parseJSON(message.body)
    });
}

function updateItemPaymentRefund(message) {
    itemPaymentRefundChart.load({
        json: $.parseJSON(message.body),
        keys: {
            x: 'item',
            value: ['PAYMENT', 'REFUND']
        }
    });
}

$(document).ready(function () {
    connect();

    currencyRevenueChart = c3.generate({
        bindto: '#currency-revenue',
        data: {
            type: 'pie',
            json: {}
        }
    });

    clientRevenueChart = c3.generate({
        bindto: '#client-revenue',
        tooltip: {
            format: {
                value: function (x) {
                    return '$' + x;
                }
            }
        },
        data: {
            type: 'donut',
            json: {}
        }
    });

    itemPaymentRefundChart = c3.generate({
        bindto: '#item-payment-refund',
        data: {
            type: 'bar',
            json: {}
        },
        axis: {
            x: {
                type: 'category'
            }
        }
    });
});
