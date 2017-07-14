package spittr.alerts;

import spittr.Spittle;
import spittr.web.exception.SpittleNotFoundException;

/**
 * Created by dell on 2017-7-13.
 */
public interface AlertService {
    void sendSpittleAlert(Spittle spittle);
    Spittle retrieveSpittleAlert();
}
