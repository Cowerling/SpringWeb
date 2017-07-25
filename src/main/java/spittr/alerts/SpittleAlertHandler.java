package spittr.alerts;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import spittr.Spittle;
import spittr.config.JMSConfig;

/**
 * Created by dell on 2017-7-14.
 */
@Component
public class SpittleAlertHandler {
    private Logger logger = LoggerFactory.getLogger(SpittleAlertHandler.class);

    @JmsListener(destination = JMSConfig.DESTINATION_QUEUE)
    public void handleSpittleAlert(Spittle spittle) {
        logger.info("handleSpittleAlert(" + spittle + ")");
    }
}
