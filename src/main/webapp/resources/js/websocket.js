$(document).ready(function () {
    //var sock = new WebSocket("ws://" + window.location.host + "/spittr/marco");
    /*var sock = new SockJS("marco");

    $(sock).on("open", function (event) {
        sayMarco();
    });

    sock.addEventListener("message", function(event) {
        $("#message").append("<li>Received message: " + event.data + "</li>");
        setTimeout(function () {
            sayMarco();
        }, 2000);
    });

    $(sock).on("close", function (event) {
        $("#message").append("<li>Closing</li>");
    });

    $(window).unload(function (event) {
        sock.close();
    });

    function sayMarco() {
        sock.send("Marco!");
    }*/

    var url = "http://" + window.location.host + "/spittr/marcopolo";
    var sock = new SockJS(url);
    var stomp = Stomp.over(sock);

    stomp.connect("guest", "guest", function (frame) {
        stomp.send("/app/marco", {}, JSON.stringify({ message: "Marco!" }));
    });
});