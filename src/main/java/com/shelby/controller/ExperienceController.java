package com.shelby.controller;

import com.shelby.entity.Experience;
import com.shelby.entity.Resume;
import com.shelby.mapper.ExperienceMapper;
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
 * @Date 2021/4/21 16:38
 * @Version 1.0
 */
@RestController
@RequestMapping("experience")
public class ExperienceController {
    protected Response result(ExceptionMsg msg){
        return new Response(msg);
    }
    protected Response result(){
        return new Response();
    }
    @Autowired
    ExperienceMapper experienceMapper;
    //增
    @ApiOperation(value = "add", notes = "增加简历经历 ")
    @RequestMapping(value = "/", method = RequestMethod.POST)
    public ResponseData add(Experience experience) {

        experienceMapper.insertExperience(experience);
        return new ResponseData(ExceptionMsg.SUCCESS, experience);
    }
    //删
    @ApiOperation(value = "delete", notes = "根据id删除一条经历 ")
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Response delete(@PathVariable("id") Integer id) {

        experienceMapper.deleteExperience(id);
        return result(ExceptionMsg.SUCCESS);
    }
    //改
    @ApiOperation(value = "update", notes = "更新一条经历 ")
    @RequestMapping(value = "/", method = RequestMethod.PUT)
    public ResponseData update(Experience experience) {
        experienceMapper.updateExperience(experience);
        return new ResponseData(ExceptionMsg.SUCCESS, experience);
    }

    // 加载经历
    @ApiOperation(value = "findExperience", notes = "根据简历id查询所有经历 ")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public ResponseData findExperience(@PathVariable("id") int id) throws IOException {
        Experience experience = experienceMapper.findById(id);
        if (experience != null) {
            return new ResponseData(ExceptionMsg.SUCCESS,experience);
        }
        return new ResponseData(ExceptionMsg.FAILED,experience);
    }
}
