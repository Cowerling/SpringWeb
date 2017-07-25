package spittr.alerts;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import spittr.Spittle;

/**
 * Created by dell on 2017-7-18.
 */
//@Component("alertService2")
@Qualifier("mail")
public class AlertServiceImpl2 implements AlertService {
    private JavaMailSender mailSender;
    private String alertEmailAddress;

    public AlertServiceImpl2(JavaMailSender mailSender, String alertEmailAddress) {
        this.mailSender = mailSender;
        this.alertEmailAddress = alertEmailAddress;
    }

    @Override
    public void sendSpittleAlert(Spittle spittle) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("surpermama@126.com");
        message.setTo(alertEmailAddress);
        message.setSubject("New spittle from2 " + spittle.getId());
        message.setText(spittle.getId() + " says: " + spittle.getMessage());
        mailSender.send(message);
    }

    @Override
    public Spittle retrieveSpittleAlert() {
        throw null;
    }
}
