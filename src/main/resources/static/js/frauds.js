function connect() {
    socket = new SockJS('/dashboard');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/frauds', function (message) {
            console.log(message);
            updateFrauds(message);
        });

        $.get("/initializeData");
    });
}

function disconnect() {
    stompClient.disconnect();
    console.log("Disconnected");
}

function updateFrauds(message) {
    $('#frauds').html("");
    var frauds = $.parseJSON(message.body);
    console.log(frauds);

    for (var i = 0; i < frauds.length; i++) {
        var fraud = frauds[i];
        var row;
        if (i % 2 == 0) {
            row = '<tr class="even pointer">';
        } else {
            row = '<tr class="odd pointer">';
        }
        row += '<td>' + fraud.accountNumber + '</td>' +
            '<td>' + fraud.fraudDate + '</td>' +
            '<td>' + fraud.itemName + '</td>' +
            '<td>' + fraud.amount + '</td>' +
            '<td>' + fraud.quantity + '</td>' +
            '<td>' + fraud.fraudReason + '</td>' +
            '</tr>';

        $('#frauds').append(row);
    }
}

$(document).ready(function () {
    connect();
});
