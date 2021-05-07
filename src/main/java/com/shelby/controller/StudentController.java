package com.shelby.controller;

import com.shelby.entity.Resume;
import com.shelby.entity.Student;
import com.shelby.mapper.ResumeMapper;
import com.shelby.mapper.StudentMapper;
import com.shelby.result.ExceptionMsg;
import com.shelby.result.Response;
import com.shelby.result.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 14:48
 * @Version 1.0
 */

@Api("学生基本操作")
@RestController
@RequestMapping("student")
public class StudentController {

    protected Response result(ExceptionMsg msg){
        return new Response(msg);
    }
    protected Response result(){
        return new Response();
    }

    @Autowired
    StudentMapper studentMapper;

    @ApiOperation(value = "getStudentList", notes = "获取学生信息列表 ")
    @RequestMapping(value = "/", method = RequestMethod.GET)
    public ResponseData getStudentList() {
        List<Student> list = new ArrayList<Student>(studentMapper.queryAll());
        return new ResponseData(ExceptionMsg.SUCCESS, list);
    }

    //增
    @ApiOperation(value = "add", notes = "增加一条学生信息 ")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseData add(Student student) {
//        studentService.save(student);
        studentMapper.add(student);
        // return "{success:true,message: \"添加成功\" }";
        return new ResponseData(ExceptionMsg.SUCCESS, student);
    }

    //删
    @ApiOperation(value = "delete", notes = "根据id删除一条学生信息")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable("id") Long id) {

        studentMapper.delById(id);
        return result(ExceptionMsg.SUCCESS);
        //return new ResponseData(ExceptionMsg.SUCCESS,"");
    }

    //改
    @ApiOperation(value = "update", notes = "更新一条学生信息 ")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseData update(Student student) {
        studentMapper.updateById(student);
        return new ResponseData(ExceptionMsg.SUCCESS, student);
    }

    //查
    @ApiOperation(value = "findStudent", notes = "根据id查找该学生信息 ")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData findStudent(@PathVariable("id") Integer id) throws IOException {
        Student student = studentMapper.queryById(id);
        if (student != null) {
            return new ResponseData(ExceptionMsg.SUCCESS,student);
        }
        return new ResponseData(ExceptionMsg.FAILED,student);
    }


}
