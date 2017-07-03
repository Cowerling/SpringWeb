package spittr.service;

import spittr.Spitter;
import spittr.Spittle;
import spittr.web.exception.DuplicateSpittleException;

import java.util.List;

/**
 * Created by dell on 2017-6-29.
 */
public interface SpitterService {
    List<Spittle> getRecentSpittles(int count);
    void saveSpittle(Spittle spittle) throws DuplicateSpittleException;
    void saveSpitter(Spitter spitter);
    Spitter getSpitter(long id);
    void startFollowing(Spitter follower, Spitter followee);
    List<Spittle> getSpittlesForSpitter(Spitter spitter);
    List<Spittle> getSpittlesForSpitter(String username);
    Spitter getSpitter(String username);
    Spittle getSpittleById(long id);
    void deleteSpittle(long id);
    List<Spitter> getAllSpitters();
}
