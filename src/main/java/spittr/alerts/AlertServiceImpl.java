package spittr.alerts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsOperations;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Repository;
import spittr.Spittle;
import spittr.web.exception.SpittleNotFoundException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * Created by dell on 2017-7-13.
 */
@Repository
public class AlertServiceImpl implements AlertService {
    @Autowired
    private JmsOperations jmsOperations;

    @Override
    public void sendSpittleAlert(final Spittle spittle) {
        jmsOperations.convertAndSend(spittle);
    }

    @Override
    public Spittle retrieveSpittleAlert() {
        return (Spittle)jmsOperations.receiveAndConvert();
    }
}
