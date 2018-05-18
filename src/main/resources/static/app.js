var stompClient = null;
var betButton;
var mul;
var bet;
let flag = true;
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


function showGreeting(message) {
    var values = message.split(":");
    switch (values[0]){
        case "tick": tick(values[1]); break;
        case "pause": pause(values[1]); break;
        case "end": end(values[1]); break;
    }
}

function end(value) {
    $("#greetings").html("<tr style='color: red'><td>" + value + "</td></tr>");
}

function pause(value) {
    if(flag){
        $.ajax({
            type : "GET",
            contentType : "application/json",
            url : window.location+"/getUser",
            dataType : 'json',
            timeout : 100000,
            success : function(data) {
                let balance = document.getElementById('balance');
                balance.innerHTML = 'Баланс: '+ data;
            },
            error : function(e) {
                console.log("ERROR: ", e);
            },
            done : function(e) {
                console.log("DONE");
            }
        });

        bet.disabled = false;
        mul.disabled = false;
        betButton.disabled = false;
        flag=false;
    }
    $("#greetings").html("<tr><td>Pause: " + value + "</td></tr>");
}


function tick(number) {
    flag= true;
    bet.disabled = true;
    mul.disabled = true;
    betButton.disabled = true;
    $("#greetings").html("<tr style='color: green'><td>" + number + "</td></tr>");
}

$(function () {
    bet = document.getElementById("mybet");
    mul = document.getElementById("multiplier");
    betButton = document.getElementById("bet");
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });

    connect();
});