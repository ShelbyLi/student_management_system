package com.shelby.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.shelby.entity.Student;
import com.shelby.mapper.StudentMapper;
import com.shelby.util.result.ExceptionMsg;
import com.shelby.util.result.Response;
import com.shelby.util.result.ResponseData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.collections.CollectionUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    private RedisTemplate<String,String> redisTemplate;

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
            return new ResponseData(ExceptionMsg.SUCCESS, student);
        }
        return new ResponseData(ExceptionMsg.FAILED, student);
    }

    @ApiOperation(value = "getTopN", notes = "用Redis快速获取绩点top n的同学")
    @RequestMapping(value = "/getTopN/{n}", method = RequestMethod.GET)
    public  ResponseData getTopN(@PathVariable("n") Long n) {
        Set<ZSetOperations.TypedTuple<String>> set = new HashSet<>();
        List<Student> students = studentMapper.getUserList();
        for (Student student:students) {
            String name = student.getName();
            double gpa = student.getGpa();
            DefaultTypedTuple<String> defaultTypedTuple = new DefaultTypedTuple<>(name, gpa);
            set.add(defaultTypedTuple);
        }
        redisTemplate.opsForZSet().add("score_rank", set);
//        System.out.println(redisTemplate.opsForZSet().range("score_rank", 0, -1));
        // 该函数为将set中的值排序 递增输出第start至end的成员. top n只需要输出末尾n人即可
        return new ResponseData(ExceptionMsg.SUCCESS, redisTemplate.opsForZSet().range("score_rank", students.size()-n, -1));
    }

//    @RequestMapping(value = "toLogin")
//    public String toLogin() {
//        return "login";
//    }
//
//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ResponseData login(String name, String pwd) {
//        // 获取当前用户
//        Subject subject = SecurityUtils.getSubject();
//        // 封装用户登录数据
//        UsernamePasswordToken token = new UsernamePasswordToken(name, pwd);
//
//        try {
//            subject.login(token); // 执行登录方法 没有异常就ok
//            return new ResponseData(ExceptionMsg.SUCCESS);
//        } catch (UnknownAccountException e) {
//            return new ResponseData(ExceptionMsg.FAILED, "用户名错误");
//        } catch (IncorrectCredentialsException e) {
//            return new ResponseData(ExceptionMsg.FAILED, "密码错误");
//        }
//    }
//
//    @RequestMapping(value = "/noauth")
//    public String unauthorized() {
//        return "未授权页面";
//    }
}
