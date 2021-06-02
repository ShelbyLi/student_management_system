package com.shelby.service.impl;

import com.github.pagehelper.Page;
import com.shelby.entity.Student;
import com.shelby.service.StudentService;
import com.shelby.mapper.StudentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author Shelby Li
 * @Date 2021/5/21 10:45
 * @Version 1.0
 */
@Service
@CacheConfig(cacheNames = "studentService")
public class StudentServiceImpl implements StudentService {
    @Autowired
    private StudentMapper studentMapper;

    @Override
    @Cacheable(key ="#p0")
    public Student queryById(Integer id) {
        return studentMapper.queryById(id);
    }

    @Override
    public List<Student> queryAll() {
        return studentMapper.queryAll();
    }

    @Override
    public int add(Student student) {
        return studentMapper.add(student);
    }

    @Override
    //如果指定为 true，则方法调用后将立即清空所有缓存
    @CacheEvict(key ="#p0",allEntries=true)
    public int delById(long id) {
        return studentMapper.delById(id);
    }

    @Override
    @CachePut(key = "#p0")
    public int updateById(Student student) {
        return studentMapper.updateById(student);
    }

    @Override
    public Page<Student> getUserList() {
        return studentMapper.getUserList();
    }

    @Override
    public List<Student> query(String name, Integer age) {
        return studentMapper.query(name, age);
    }
}
