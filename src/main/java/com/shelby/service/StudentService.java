package com.shelby.service;

import com.github.pagehelper.Page;
import com.shelby.entity.Student;

import java.util.List;

/**
 * @Author Shelby Li
 * @Date 2021/5/24 14:22
 * @Version 1.0
 */

public interface StudentService {
        public Student queryById(Integer id);
    public List<Student> queryAll();
    public int add(Student student);
    public int delById(long id);
    public int updateById(Student student);
    public Page<Student> getUserList();
    public List<Student> query(String name, Integer age);
}
