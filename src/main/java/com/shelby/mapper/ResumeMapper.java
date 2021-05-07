package com.shelby.mapper;

import com.shelby.entity.Experience;
import com.shelby.entity.Resume;
import org.apache.ibatis.annotations.*;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 16:02
 * @Version 1.0
 */

@Mapper
public interface ResumeMapper {
    @Select("SELECT * FROM resume WHERE id =#{id}")
    public Resume findById(Integer id);

    @Insert("INSERT INTO resume(content,stu_id) " +
            "values (#{content},#{stuId})")
    public int insertResume(Resume resume);

    @Update("UPDATE resume SET content=#{content} WHERE id=#{id}")
    public int updateResume(Resume resume);

    @Delete("DELETE FROM resume WHERE id=#{id}")
    public int deleteResume(Integer id);

    @Select("SELECT * FROM resume WHERE stu_id =#{stuId}")
    public Resume findByStuId(Long stuId);
}
