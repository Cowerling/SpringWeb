package spittr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import spittr.Spitter;
import spittr.Spittle;
import spittr.data.SpitterRepository;
import spittr.data.SpittleRepository;
import spittr.web.exception.DuplicateSpittleException;

import java.util.List;

/**
 * Created by dell on 2017-6-29.
 */
@Service
@Qualifier("default")
public class SpitterServiceImpl implements SpitterService {
    @Autowired
    @Qualifier("default")
    private SpitterRepository spitterRepository;

    @Autowired
    @Qualifier("default")
    private SpittleRepository spittleRepository;

    @Override
    public List<Spittle> getRecentSpittles(int count) {
        return spittleRepository.findSpittles(100, count);
    }

    @Override
    public void saveSpittle(Spittle spittle) throws DuplicateSpittleException {
        spittleRepository.save(spittle);
    }

    @Override
    public void saveSpitter(Spitter spitter) {
        spitterRepository.save(spitter);
    }

    @Override
    public Spitter getSpitter(long id) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void startFollowing(Spitter follower, Spitter followee) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Spittle> getSpittlesForSpitter(Spitter spitter) {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Spittle> getSpittlesForSpitter(String username) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Spitter getSpitter(String username) {
        return spitterRepository.findByUsername(username);
    }

    @Override
    public Spittle getSpittleById(long id) {
        return spittleRepository.findOne(id);
    }

    @Override
    public void deleteSpittle(long id) {
        spittleRepository.remove(id);
    }

    @Override
    public List<Spitter> getAllSpitters() {
        throw new UnsupportedOperationException();
    }
}
