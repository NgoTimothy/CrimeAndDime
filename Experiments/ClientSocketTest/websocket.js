var ws;
var lobbyId;
var myMoney;

function connect() {
    var username = document.getElementById("username").value;

    var host = document.location.host;
    var pathname = document.location.pathname;

    //ws = new WebSocket("ws://coms-309-tc-3.misc.iastate.edu:8081/websocket/" + username);
    ws = new WebSocket("ws://" +"localhost:8080"+"/websocket" + "/"+username);
    document.getElementById("lobbyButton").style.display = "inline";
    ws.onmessage = function(event) {
        var log = document.getElementById("log");
        console.log(event.data);
        log.innerHTML += event.data + "\n";
    };
}

function send() {
    var content = document.getElementById("msg").value;

    ws.send(content);
}

function setLobbyId() {
    if(typeof ws === 'undefined') {
        return;
    }
    lobbyId = document.getElementById("lobbyId").value;
    if(!isInt(lobbyId)) {
        lobbyId = "1";
    }
    document.getElementById("moneyButton").style.display = "inline";
    ws.send("joinlobby " + lobbyId);
}

function setMoney() {
    if(typeof ws === 'undefined') {
        return;
    }
    myMoney = document.getElementById("money").value;
    if(Number.isNaN(myMoney)) {
        myMoney = "1";
    }
    ws.send("sendmymoney " + myMoney);
}

function isInt(value) {
    var x = parseFloat(value);
    return !isNaN(value) && (x | 0) === x;
}