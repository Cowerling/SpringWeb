package spittr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import spittr.Spittle;
import spittr.web.exception.DuplicateSpittleException;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by dell on 2017-3-24.
 */
@Repository
public class JdbcSpittleRepository implements SpittleRepository {
    @Autowired
    private JdbcOperations jdbcOperations;

    private static final String SELECT_SPITTLES_BY_COUNT = "SELECT TOP ? message, postedTime FROM Spittle";
    private static final String SELECT_SPITTLE_BY_ID = "SELECT message, postedTime FROM spittle WHERE id = ?";
    private static final String INSERT_SPITTLE = "INSERT INTO spittle(id, message, postedTime) VALUES (?, ?, ?)";

    public List<Spittle> findSpittles(long max, int count) {
        List<Spittle> spittles = new ArrayList<Spittle>();

        for(Map row : jdbcOperations.queryForList(SELECT_SPITTLES_BY_COUNT, count)) {
            spittles.add(new Spittle((String)row.get("message"), (Date)row.get("postedTime")));
        }

        return spittles;
    }

    public Spittle findOne(long spittleId) {
        return jdbcOperations.queryForObject(SELECT_SPITTLE_BY_ID, new RowMapper<Spittle>() {
            @Override
            public Spittle mapRow(ResultSet resultSet, int i) throws SQLException {
                return new Spittle(
                        resultSet.getString("message"),
                        resultSet.getTime("postedTime")
                );
            }
        }, spittleId);
    }

    public Spittle save(Spittle spittle) throws DuplicateSpittleException {
        jdbcOperations.update(INSERT_SPITTLE,
                spittle.getId(),
                spittle.getMessage(),
                spittle.getTime());

        return spittle;
    }

    public void remove(long spittleId) {}
}
