package spittr.data;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import spittr.Spitter;
import spittr.data.mapper.SpitterMapper;

/**
 * Created by dell on 2017-6-25.
 */
@Repository
@Qualifier("default")
public class MybatisSpitterRepository implements SpitterRepository {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private SqlSession currentSession() {
        return sqlSessionFactory.openSession();
    }

    public Spitter save(Spitter spitter) {
        SqlSession sqlSession = currentSession();

        try {
            SpitterMapper spitterMapper = sqlSession.getMapper(SpitterMapper.class);
            spitterMapper.insertSpitter(spitter);
            return spitter;
        } finally {
            sqlSession.close();
        }
    }

    public Spitter findByUsername(String username) {
        SqlSession sqlSession = currentSession();

        try {
            SpitterMapper spitterMapper = sqlSession.getMapper(SpitterMapper.class);
            return spitterMapper.selectSpitterByUsername(username);
        } finally {
            sqlSession.close();
        }
    }
}
