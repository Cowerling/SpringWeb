package spittr.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import spittr.Spittle;
import spittr.alerts.AlertService;
import spittr.web.exception.SpittleNotFoundException;

import java.util.Date;

/**
 * Created by dell on 2017-7-13.
 */
@RestController
@RequestMapping("/jms")
public class JMSController {
    @Autowired
    private AlertService alertService;

    /*@Autowired
    @Qualifier("remote")
    private AlertService mailAlertService;*/

    @RequestMapping(value = "/send", method = RequestMethod.GET)
    public Spittle send() {
        Spittle spittle = new Spittle(61616L, "JmsTemplate", new Date(), 0d, 0d);
        alertService.sendSpittleAlert(spittle);
        return spittle;
    }

    @RequestMapping("/retrieve")
    public Spittle retrieve() throws SpittleNotFoundException {
        return alertService.retrieveSpittleAlert();
    }

    /*@RequestMapping("/mail")
    public Spittle sendMail() {
        Spittle spittle = new Spittle(61616L, "JmsTemplate", new Date(), 0d, 0d);
        mailAlertService.sendSpittleAlert(spittle);
        return spittle;
    }*/
}
