package com.shelby.mapper;

import com.github.pagehelper.Page;
import com.shelby.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 14:58
 * @Version 1.0
 */


@Mapper
public interface StudentMapper {
    public Student queryById(Integer id);

//    public int updateStudent(Student student);
//    @Select("SELECT * FROM user WHERE id = #{id}")
//    Student queryById(@Param("id") long id);
//    //   @Select("SELECT * FROM user limit 1000 ")  //去掉limit 1000
    @Select("SELECT * FROM student   ")
    List<Student> queryAll();

    @Insert({"INSERT INTO student(name,age) VALUES(#{name},#{age})"})
    int add(Student user);

    @Delete("DELETE FROM student WHERE id = #{id}")
    int delById(long id);

    @Update("UPDATE student SET name=#{name},age=#{age} WHERE id = #{id}")
    int updateById(Student student);

    @Select("SELECT * FROM student ")
    Page<Student> getUserList();

    public List<Student> query(String name, Integer age);
}
