package com.shelby.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shelby.entity.Resume;
import com.shelby.entity.Student;
import com.shelby.mapper.ResumeMapper;
import com.shelby.mapper.StudentMapper;
import com.shelby.result.ExceptionMsg;
import com.shelby.result.Response;
import com.shelby.result.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @ApiOperation(value = "getStudentListPage", notes = "获取学生信息列表 ")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseData getStudentListPage(@ApiParam(name="currentPage",value="当前页") @RequestParam(defaultValue = "1")Integer currentPage,
                                           @ApiParam(name="limit",value="每页数量") @RequestParam(defaultValue = "20")Integer limit,
                                           @ApiParam(name="name",value="模糊查询姓名") @RequestParam(defaultValue = "")String name,
                                           @ApiParam(name="name",value="年龄") @RequestParam(defaultValue = "")Integer age)
//                                           @ApiParam(name="sort",value="排序方法") @RequestParam(defaultValue = "")String sort)
    {
//        PageHelper.startPage(currentPage, limit,"id desc");
        PageHelper.startPage(currentPage, limit);
        //3. 因为PageHelper的作用，这里就会返回当前分页的集合了
        List<Student> list = studentMapper.query(name, age);
        //4. 根据返回的集合，创建PageInfo对象
        PageInfo<Student> page = new PageInfo<>(list);

//        List<Student> list = new ArrayList<Student>(studentMapper.query(currentPage, limit, name, age));
//        List<Student> list = new ArrayList<Student>(studentMapper.query(currentPage, limit, name, age, sort));
        return new ResponseData(ExceptionMsg.SUCCESS, page);
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
