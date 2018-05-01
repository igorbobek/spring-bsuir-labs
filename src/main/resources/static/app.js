var stompClient = null;

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);

}

function connect() {
    var socket = new SockJS('/websocket');
    stompClient = Stomp.over(socket);
    stompClient.debug = null;
    stompClient.connect({}, function (frame) {
        setConnected(true);
        //console.log('Connected: ' + frame);
        stompClient.subscribe('/game/crash', function (greeting) {
            setTimeout(function() {
                showGreeting(JSON.parse(greeting.body).content);
            }, 0);
        });
        stompClient.subscribe('/user/game/crash', function (greeting) {
            setTimeout(function() {
                showNotification(JSON.parse(greeting.body).content);
            }, 0);
        });
    });
}

function showNotification(message) {
    var notif = document.getElementById("notification");
    notif.hidden = false;
    notif.innerText = message;
    setTimeout(function () {
        notif.hidden = true;
    }, 3000)

}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}


function showGreeting(message) {
    var values = message.split(":");
    switch (values[0]){
        case "tick": tick(values[1])
    }
}

function tick(number) {
    $("#greetings").html("<tr><td>" + number + "</td></tr>");
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
    $("#connect").trigger("click");
});