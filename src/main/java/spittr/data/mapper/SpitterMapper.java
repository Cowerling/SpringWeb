package spittr.data.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import spittr.Spitter;

import java.util.List;

/**
 * Created by dell on 2017-6-25.
 */
public interface SpitterMapper {
    @Insert("INSERT INTO Spitter(username, password, first_name, last_name, email) VALUES(#{username}, #{password}, #{firstName}, #{lastName}, #{email})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    Spitter insertSpitter(Spitter spitter);

    @Select("SELECT * FROM Spitter WHERE id = #{id}")
    @ResultMap("spittr.data.mapper.SpitterMapper.spitterResult")
    Spitter selectSpitterById(long id);

    @Select("SELECT * FROM Spitter WHERE username = #{username}")
    @ResultMap("spittr.data.mapper.SpitterMapper.spitterResult")
    Spitter selectSpitterByUsername(String username);

    @Select("SELECT * FROM Spitter")
    @ResultMap("spittr.data.mapper.SpitterMapper.spitterResult")
    List<Spitter> selectAllSpitters();
}
