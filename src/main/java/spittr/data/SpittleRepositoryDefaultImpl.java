package spittr.data;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import spittr.Spittle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cowerling on 17-1-12.
 */
@Component
@Qualifier("default")
public class SpittleRepositoryDefaultImpl implements SpittleRepository {
    public List<Spittle> findSpittles(long max, int count) {
        List<Spittle> spittles = new ArrayList<Spittle>();
        for (int i = 0; i < count; i++) {
            spittles.add(new Spittle("Spittle " + i, new Date(), (double)i, (double)i));
        }
        return spittles;
    }

    public Spittle findOne(long spittleId) {
        return new Spittle("Spittle " + spittleId, new Date());
    }
}
