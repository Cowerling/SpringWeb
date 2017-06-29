package spittr.data;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spittr.Spittle;
import spittr.web.exception.DuplicateSpittleException;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by cowerling on 17-1-12.
 */
@Component
public class SpittleRepositoryDefaultImpl implements SpittleRepository {
    private Map<Long, Spittle> spittleMap = new HashMap<Long, Spittle>();

    public List<Spittle> findSpittles(long max, int count) {
        List<Spittle> spittles = spittleMap.entrySet().stream()
                .filter(item -> item.getKey() < max)
                .map(item -> item.getValue())
                .collect(Collectors.toList());

        return spittles.size() > 0 ? spittles.subList(0, spittles.size() > count ? count : spittles.size()) : new ArrayList<Spittle>();
    }

    public Spittle findOne(long spittleId) {
        return spittleMap.get(spittleId);
    }

    public Spittle save(Spittle spittle) throws DuplicateSpittleException {
        if (spittleMap.containsKey(spittle.getId()))
            throw new DuplicateSpittleException();

        return spittleMap.put(spittle.getId(), spittle);
    }

    public void remove(long spittleId) {}
}
