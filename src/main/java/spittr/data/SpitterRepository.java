package spittr.data;

import spittr.Spitter;

/**
 * Created by dell on 2017-2-2.
 */
public interface SpitterRepository {
    Spitter save(Spitter spitter);
    Spitter findByUsername(String username);
}
