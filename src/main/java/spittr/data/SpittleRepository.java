package spittr.data;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreFilter;
import spittr.Spittle;
import spittr.web.exception.DuplicateSpittleException;

import java.util.List;

/**
 * Created by dell on 2017-1-10.
 */
public interface SpittleRepository {
    @PostFilter("filterObject.message.length() <= 100")
    List<Spittle> findSpittles(long max, int count);
    @Cacheable(value = "spittleCache", unless = "#result.message.contains('NoCache')", condition = "#id >= 10")
    Spittle findOne(long spittleId);
    @CachePut(value = "spittleCache", key = "#result.id")
    Spittle save(Spittle spittle) throws DuplicateSpittleException;
    @CacheEvict("spittleCache")
    @PreFilter("hasPermission(targetObject, 'delete')")
    void remove(long spittleId);
}
