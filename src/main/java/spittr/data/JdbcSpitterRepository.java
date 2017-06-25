package spittr.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;
import org.springframework.stereotype.Repository;
import spittr.Spitter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by dell on 2017-3-21.
 */
@Repository
public class JdbcSpitterRepository implements SpitterRepository {
    @Autowired
    //private JdbcOperations jdbcOperations;
    private NamedParameterJdbcOperations namedParameterJdbcOperations;

    private static final String INSERT_SPITTER = "INSERT INTO spitter(id, username, password, first_name, last_name, email) VALUES (:id, :username, :password, :first_name, :last_name, :email)";
    private static final String SELECT_SPITTER_BY_USERNAME = "SELECT id, username, password, first_name, last_name, email FROM spitter WHERE username = :username";

    public Spitter save(Spitter spitter) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("id", spitter.getId());
        paramMap.put("username", spitter.getUsername());
        paramMap.put("password", spitter.getPassword());
        paramMap.put("first_name", spitter.getFirstName());
        paramMap.put("last_name", spitter.getLastName());
        paramMap.put("email", spitter.getEmail());

        namedParameterJdbcOperations.update(INSERT_SPITTER, paramMap);

        return spitter;
    }

    public Spitter findByUsername(String username) {
        Map<String, Object> paramMap = new HashMap<String, Object>();
        paramMap.put("username", username);

        return namedParameterJdbcOperations.queryForObject(
                SELECT_SPITTER_BY_USERNAME,
                paramMap,
                (resultSet, i) -> {
                    return new Spitter(
                            resultSet.getLong("id"),
                            resultSet.getString("username"),
                            resultSet.getString("password"),
                            resultSet.getString("first_name"),
                            resultSet.getString("last_name"),
                            resultSet.getString("email")
                    );
                });
    }
}
