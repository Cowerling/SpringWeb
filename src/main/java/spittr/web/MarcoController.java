package spittr.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import spittr.marcopolo.Shout;

@Controller
public class MarcoController {
    private static final Logger logger = LoggerFactory.getLogger(MarcoController.class);

    @MessageMapping("/marco")
    public Shout handleShout(Shout incoming) {
        logger.info("Received message: " + incoming.getMessage());
        Shout outgoing = new Shout();
        outgoing.setMessage("Poo!");
        return outgoing;
    }

    @SubscribeMapping({"/marcosub"})
    public Shout handleSubscription() {
        Shout outgoing = new Shout();
        outgoing.setMessage("Polo!");
        return outgoing;
    }
}
