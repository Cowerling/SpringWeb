package spittr.remoting.jaxws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import spittr.Spitter;
import spittr.Spittle;
import spittr.service.SpitterService;
import spittr.web.exception.DuplicateSpittleException;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.util.List;

/**
 * Created by dell on 2017-6-30.
 */
@Component
@WebService(serviceName = "SpitterService")
public class SpitterServiceEndpoint {
    @Autowired
    SpitterService spitterService;

    @WebMethod
    public void addSpittle(Spittle spittle) {
        try {
            spitterService.saveSpittle(spittle);
        } catch (DuplicateSpittleException e) {

        }
    }

    @WebMethod
    public void deleteSpittle(long spittleId) {
        spitterService.deleteSpittle(spittleId);
    }

    @WebMethod
    public List<Spittle> getRecentSpittles(int spittleCount) {
        return spitterService.getRecentSpittles(spittleCount);
    }

    @WebMethod
    public List<Spittle> getSpittlesForSpitter(Spitter spitter) {
        return spitterService.getSpittlesForSpitter(spitter);
    }
}
