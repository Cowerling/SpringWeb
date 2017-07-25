$(document).ready(function () {
    var sock = new SockJS("marcopolo");
    var stomp = Stomp.over(sock);

    stomp.connect("guest", "guest", function (frame) {
        var $spittleList = $("ul.spittleList");

        stomp.subscribe("/topic/spittlefeed", function (incoming) {
            var spittle = $.parseJSON(incoming.body);

            $("<div></div>").appendTo($spittleList).addClass("spittleMessage").text(spittle.message);
            $("<div></div>").appendTo($spittleList)
                .append("<span class='spittleTime'>" + spittle.time + "</span>")
                .append("<span class='spittleLocation'>{" + spittle.latitude + ", " + spittle.longitude + "}</span>")

            stomp.send("/app/spittle", {}, incoming.body);
        });

        stomp.subscribe("/user/queue/notifications", function (incoming) {
            var notification = $.parseJSON(incoming.body);
            $("<div></div>").appendTo($spittleList).addClass("spittleMessage").text(notification.message);
        });
    });
});