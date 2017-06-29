package spittr.data.mapper;

import org.apache.ibatis.annotations.*;
import spittr.Spittle;
import spittr.web.exception.DuplicateSpittleException;

import java.util.List;

/**
 * Created by dell on 2017-6-27.
 */
public interface SpittleMapper {
    @Select("SELECT TOP #{count} message, postedTime, latitude, longitude FROM Spittle")
    @ResultMap("spittr.data.mapper.SpittleMapper.spittleResult")
    List<Spittle> selectAll(@Param("max") long max, @Param("count") int count);

    @Select("SELECT * FROM Spittle WHERE id = #{spittleId}")
    @ResultMap("spittr.data.mapper.SpittleMapper.spittleResult")
    Spittle selectById(long spittleId);

    @Insert("INSERT INTO Spittle(message, postedTime, latitude, longitude) VALUES(#{message}, #{time}, #{latitude}, #{longitude})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Spittle spittle) throws DuplicateSpittleException;

    @Delete("DELETE FROM Spittle WHERE id = #{spittleId}")
    void delete(long spittleId);
}
