package spittr.data;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Repository;
import spittr.Spittle;
import spittr.data.mapper.SpittleMapper;
import spittr.web.exception.DuplicateSpittleException;

import javax.annotation.security.RolesAllowed;
import java.util.List;

/**
 * Created by dell on 2017-6-27.
 */
@Repository
@Qualifier("default")
public class MybatisSpittleRepository implements SpittleRepository {
    @Autowired
    private SqlSessionFactory sqlSessionFactory;

    private SqlSession currentSession() {
        return sqlSessionFactory.openSession();
    }

    public List<Spittle> findSpittles(long max, int count) {
        SqlSession sqlSession = currentSession();

        try {
            SpittleMapper spittleMapper = sqlSession.getMapper(SpittleMapper.class);
            return spittleMapper.selectAll(max, count);
        } finally {
            sqlSession.close();
        }
    }

    public Spittle findOne(long spittleId) {
        SqlSession sqlSession = currentSession();

        try {
            SpittleMapper spittleMapper = sqlSession.getMapper(SpittleMapper.class);
            return spittleMapper.selectById(spittleId);
        } finally {
            sqlSession.close();
        }
    }

    @RolesAllowed({"ROLE_SPITTER"})
    @PreAuthorize("hasRole('ROLE_SPITTER') and #spittle.message.length() <= 100")
    public Spittle save(Spittle spittle) throws DuplicateSpittleException {
        SqlSession sqlSession = currentSession();

        try {
            SpittleMapper spittleMapper = sqlSession.getMapper(SpittleMapper.class);
            spittleMapper.insert(spittle);
            sqlSession.commit();
            return spittle;
        } finally {
            sqlSession.close();
        }
    }

    public void remove(long spittleId) {
        SqlSession sqlSession = currentSession();

        try {
            SpittleMapper spittleMapper = sqlSession.getMapper(SpittleMapper.class);
            spittleMapper.delete(spittleId);
            sqlSession.commit();
        } finally {
            sqlSession.close();
        }
    }
}
