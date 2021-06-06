var stompClient = null;
var stompClient2 = null;

connect();
setInterval(sendName, 5000);
setInterval(sendRequest, 10000);

function setConnected(connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
    }
    else {
        $("#conversation").hide();
    }
    $("#greetings").html("");
}

function connect() {
    var socket = new SockJS('/alert');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, function (frame) {
        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient.subscribe('/topic/greetings', function (updateAlerts) {
            showGreeting(updateAlerts);
        });
    });
    
    var socket2 = new SockJS('/positions');
    stompClient2 = Stomp.over(socket2);
    stompClient2.connect({}, function (frame) {

        setConnected(true);
        console.log('Connected: ' + frame);
        stompClient2.subscribe('/topic/pos', function (updatePos) {
            showPos(updatePos);
        });
    });
  
}

function disconnect() {
    if (stompClient !== null) {
        stompClient.disconnect();
    }
    setConnected(false);
    console.log("Disconnected");
}

function sendName() {
    console.log("REQUESTING INFO");
    stompClient.send("/app/hello", {});
}

function sendRequest() {
    console.log("REQUESTING INFO");
    stompClient2.send("/app/pos_msg", {});
}

function showGreeting(message) {
    console.log(" IN greeting!!!");
    console.log(message);
    message = JSON.parse(message.body);
    
    for(var i = 0; i< message.length; i++){
    	   console.log("ALERTING");
	   alert(message[i]);
    } 
}

function showPos(message) {
    message = JSON.parse(message.body);
    
    var mapProp= {
        center:new google.maps.LatLng(40.06479009,-8.16042933),
        zoom: 21,
    };
    var map = new google.maps.Map(document.getElementById("googleMap"),mapProp);
   
    for(var i = 0; i< message.length; i++){
        if (i+3 < message.length ){
		new google.maps.Marker({position: new google.maps.LatLng(message[i][0], message[i][1]), 
		    icon: {
		        url: 'https://img.icons8.com/dotty/2x/fireman-male.png',
		        scaledSize: new google.maps.Size(20, 20)
		    }
		}).setMap(map);
        }
        else{
        	new google.maps.Marker({position: new google.maps.LatLng(message[i][0], message[i][1]), 
		    icon: {
		        url: 'https://img.icons8.com/dotty/2x/fireman-male.png',
		        scaledSize: new google.maps.Size(35, 35)
		    }
		}).setMap(map);
        }
    }
}

$(function () {
    $("form").on('submit', function (e) {
        e.preventDefault();
    });
    $( "#connect" ).click(function() { connect(); });
    $( "#disconnect" ).click(function() { disconnect(); });
    $( "#send" ).click(function() { sendName(); });
});
