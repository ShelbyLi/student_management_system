package com.shelby.controller;

import com.shelby.entity.ClubMember;
import com.shelby.entity.Student;
import com.shelby.service.ClubMemberService;
import com.shelby.service.StudentService;
import com.shelby.util.mq.Sender;
import com.shelby.util.result.ExceptionMsg;
import com.shelby.util.result.Response;
import com.shelby.util.result.ResponseData;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author Shelby Li
 * @Date 2021/6/2 18:57
 * @Version 1.0
 */
@RestController
@RequestMapping("club/member")
public class ClubMemberController {

    protected Response result(ExceptionMsg msg){
        return new Response(msg);
    }
    protected Response result(){
        return new Response();
    }
    @Autowired
    ClubMemberService clubMemberService;
    @Autowired
    StudentService studentService;
    @Autowired
    private Sender sender;

    //增
    @ApiOperation(value = "add", notes = "增加社团新成员")
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseData add(ClubMember clubMember) {

        clubMemberService.add(clubMember);
        Student newMemberInfo = studentService.queryById(clubMember.getStudentId());
        sender.send(newMemberInfo);
        return new ResponseData(ExceptionMsg.SUCCESS, clubMember);
    }
}
