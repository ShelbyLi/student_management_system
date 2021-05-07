package com.shelby.controller;

import com.shelby.entity.Resume;
import com.shelby.entity.Student;
import com.shelby.mapper.ResumeMapper;
import com.shelby.result.ExceptionMsg;
import com.shelby.result.Response;
import com.shelby.result.ResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 16:23
 * @Version 1.0
 */
@RestController
@RequestMapping("resume")
public class ResumeController {
    protected Response result(ExceptionMsg msg){
        return new Response(msg);
    }
    protected Response result(){
        return new Response();
    }

    @Autowired
    ResumeMapper resumeMapper;
    //增
    @ApiOperation(value = "add", notes = "增加简历 ")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseData add(Resume resume) {

        resumeMapper.insertResume(resume);
        return new ResponseData(ExceptionMsg.SUCCESS, resume);
    }

    //删
    @ApiOperation(value = "delete", notes = "根据简历id删除一份简历 ")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable("id") Integer id) {

        resumeMapper.deleteResume(id);
        return result(ExceptionMsg.SUCCESS);
    }

    //改
    @ApiOperation(value = "update", notes = "更新简历信息 ")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseData update(Resume resume) {
        resumeMapper.updateResume(resume);
        return new ResponseData(ExceptionMsg.SUCCESS, resume);
    }

    // 查看简历
    @ApiOperation(value = "findResume", notes = "根据学生id查找简历 ")
    @RequestMapping(value = "/{stu_id}", method = RequestMethod.GET)
    public ResponseData findResume(@PathVariable("stu_id") Long stuId) throws IOException {
        Resume resume = resumeMapper.findByStuId(stuId);
        if (resume != null) {
            return new ResponseData(ExceptionMsg.SUCCESS,resume);
        }
        return new ResponseData(ExceptionMsg.FAILED,resume);
    }
}
