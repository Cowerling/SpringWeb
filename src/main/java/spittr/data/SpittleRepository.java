package spittr.data;

import spittr.Spittle;

import java.util.List;

/**
 * Created by dell on 2017-1-10.
 */
public interface SpittleRepository {
    List<Spittle> findSpittles(long max, int count);
}
