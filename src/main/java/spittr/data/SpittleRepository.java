package spittr.data;

import spittr.Spittle;
import spittr.web.exception.DuplicateSpittleException;

import java.util.List;

/**
 * Created by dell on 2017-1-10.
 */
public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);
    Spittle findOne(long spittleId);
    void save(Spittle spittle) throws DuplicateSpittleException;
}
