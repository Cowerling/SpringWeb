package spittr.alerts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import spittr.Spittle;

@Component
@RabbitListener(containerFactory = "rabbitListenerContainerFactory", queues = {"spittle.alert.queue"})
public class RabbitSpittleAlertHandler {
    private static Logger logger = LoggerFactory.getLogger(RabbitSpittleAlertHandler.class);

    @RabbitHandler
    public void handleSpittleAlert(Spittle spittle) {
        logger.info("handleSpittleAlert(" + spittle + ")");
    }
}
