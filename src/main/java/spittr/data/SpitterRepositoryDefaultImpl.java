package spittr.data;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spittr.Spitter;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017-2-3.
 */
@Component
@Qualifier("default")
public class SpitterRepositoryDefaultImpl implements SpitterRepository {
    private Map<String, Spitter> spitters = new HashMap<String, Spitter>();

    public Spitter save(Spitter spitter) {
        spitters.put(spitter.getUsername(), spitter);
        return spitter;
    }

    public Spitter findByUsername(String username) {
        return spitters.get(username);
    }
}
