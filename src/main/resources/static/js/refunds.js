function connect() {
    socket = new SockJS('/dashboard');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/refundTransactions', function (message) {
            console.log(message);
            updateRefundResults(message);
        });

        $.get("/initializeData");
    });
}

function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
}

function updateRefundResults(message) {
    $('#refund-results').html("");
    var refundTransactions = $.parseJSON(message.body);

    for (var i = 0; i < refundTransactions.length; i++) {
        var refundTransaction = refundTransactions[i];
        var row;
        if (i % 2 == 0) {
            row = '<tr class="even pointer">';
        } else {
            row = '<tr class="odd pointer">';
        }
        row += '<td>' + refundTransaction.paymentId + '</td>' +
            '<td>' + refundTransaction.paymentDate + '</td>' +
            '<td>' + refundTransaction.itemName + '</td>' +
            '<td>' + refundTransaction.amount / 100 + ' USD</td>' +
            '<td>' + refundTransaction.quantity + '</td>' +
            '<td>' + refundTransaction.paymentStatus + '</td>' +
            '</tr>';

        $('#refund-results').append(row);
    }
}

$(document).ready(function () {
    connect();
});
