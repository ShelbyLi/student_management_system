package com.shelby.mapper;

import com.shelby.entity.Experience;
import org.apache.ibatis.annotations.*;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 16:02
 * @Version 1.0
 */

@Mapper
public interface ExperienceMapper {
    @Select("SELECT * FROM experience WHERE id =#{id}")
    public Experience findById(Integer id);

    @Insert("INSERT INTO experience(content,time,resume_id) " +
            "values (#{content},#{time},#{resumeId})")
    public int insertExperience(Experience comment);

    @Update("UPDATE experience SET content=#{content} WHERE id=#{id}")
    public int updateExperience(Experience comment);

    @Delete("DELETE FROM experience WHERE id=#{id}")
    public int deleteExperience(Integer id);

}
