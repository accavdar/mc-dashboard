function connect() {
    socket = new SockJS('/dashboard');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/paymentTransactions', function (message) {
            console.log(message);
            updatePaymentResults(message);
        });

        $.get("/initializeData");
    });
}

function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
}

function updatePaymentResults(message) {
    $('#payment-results').html("");
    var paymentTransactions = $.parseJSON(message.body);

    for (var i = 0; i < paymentTransactions.length; i++) {
        var paymentTransaction = paymentTransactions[i];
        var row;
        if (i % 2 == 0) {
            row = '<tr class="even pointer">';
        } else {
            row = '<tr class="odd pointer">';
        }
        row += '<td>' + paymentTransaction.paymentId + '</td>' +
            '<td>' + paymentTransaction.paymentDate + '</td>' +
            '<td>' + paymentTransaction.itemName + '</td>' +
            '<td>' + paymentTransaction.amount / 100 + ' USD</td>' +
            '<td>' + paymentTransaction.quantity + '</td>' +
            '<td>' + paymentTransaction.paymentStatus + '</td>' +
            '</tr>';

        $('#payment-results').append(row);
    }
}

$(document).ready(function () {
    connect();
});
