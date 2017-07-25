package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import spittr.Spittle;
import spittr.alerts.RabbitAlertService;
import spittr.web.exception.SpittleNotFoundException;

import java.util.Date;

@RestController
@RequestMapping("/amqp")
public class AMQPController {
    @Autowired
    private RabbitAlertService rabbitAlertService;

    @RequestMapping("/send")
    public Spittle send() {
        Spittle spittle = new Spittle(61616L, "JmsTemplate", new Date(), 0d, 0d);
        rabbitAlertService.sendSpittleAlert(spittle);
        return spittle;
    }

    @RequestMapping("/retrieve")
    public Spittle retrieve() throws SpittleNotFoundException {
        Spittle spittle = rabbitAlertService.retrieveSpittleAlert();
        if (spittle == null) {
            throw new SpittleNotFoundException();
        }
        return spittle;
    }
}
