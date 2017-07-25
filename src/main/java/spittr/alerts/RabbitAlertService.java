package spittr.alerts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import spittr.Spittle;

@Component
public class RabbitAlertService implements AlertService {
    private static Logger logger = LoggerFactory.getLogger(RabbitAlertService.class);

    //@Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public void sendSpittleAlert(final Spittle spittle) {
        rabbitTemplate.convertAndSend(spittle);
    }

    @Override
    public Spittle retrieveSpittleAlert() {
        Spittle spittle = (Spittle) rabbitTemplate.receiveAndConvert();
        return spittle;
    }
}
