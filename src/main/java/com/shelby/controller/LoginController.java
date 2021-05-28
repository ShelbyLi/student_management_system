package com.shelby.controller;

/**
 * @Author Shelby Li
 * @Date 2021/5/15 19:54
 * @Version 1.0
 */

import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import com.shelby.entity.Admin;
import com.shelby.mapper.AdminMapper;
import com.shelby.service.AdminService;
import com.shelby.util.Jwt.JwtUtils;
import com.shelby.util.result.ExceptionMsg;
import com.shelby.util.result.Response;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


@RestController
@Slf4j
public class LoginController {
    @Autowired
    AdminService adminService;

    protected Response result(ExceptionMsg msg){
        return new Response(msg);
    }
    protected Response result(){
        return new Response();
    }

    @PostMapping(value = "/login")
    public Object userLogin(@RequestParam(name = "username", required = true) String userName,
                            @RequestParam(name = "password", required = true) String password, ServletResponse response) {

        // 获取当前用户主体
        Subject subject = SecurityUtils.getSubject();
        String msg = null;
        boolean loginSuccess = false;
        // 将用户名和密码封装成 UsernamePasswordToken 对象
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        try {
            subject.login(token);
            msg = "登录成功。";
            loginSuccess = true;
        } catch (UnknownAccountException uae) { // 账号不存在
            msg = "用户名与密码不匹配，请检查后重新输入！";
        } catch (IncorrectCredentialsException ice) { // 账号与密码不匹配
            msg = "用户名与密码不匹配，请检查后重新输入！";
        } catch (LockedAccountException lae) { // 账号已被锁定
            msg = "该账户已被锁定，如需解锁请联系管理员！";
        } catch (AuthenticationException ae) { // 其他身份验证异常
            msg = "登录异常，请联系管理员！";
        }
//        BaseResponse<Object> ret = new BaseResponse<Object>();
        if (loginSuccess) {
            // 若登录成功，签发 JWT token
            String jwtToken = JwtUtils.sign(userName, JwtUtils.SECRET);
            // 将签发的 JWT token 设置到 HttpServletResponse 的 Header 中
            ((HttpServletResponse) response).setHeader(JwtUtils.AUTH_HEADER, jwtToken);
            //
            log.info("");

            return result(ExceptionMsg.SUCCESS);
        } else {
            return new Response("401", msg);
        }

    }

    @GetMapping("/logout")
    public Object logout() {
        return result(ExceptionMsg.SUCCESS);
    }

    @PostMapping("/register")
    public Object register(@RequestParam(name = "username", required = true) String userName,
                           @RequestParam(name = "password", required = true) String password, ServletResponse response) {
        ByteSource salt = ByteSource.Util.bytes(userName);
        String newPs = new SimpleHash("SHA-1", password, salt, 16).toHex();
        Map<String, String> dataMap = new HashMap<>();
        dataMap.put("name", userName);
        dataMap.put("password", newPs);
        Admin admin = adminService.queryByName(userName);
        if (admin == null) {
            adminService.insertData(dataMap);
            return result(ExceptionMsg.SUCCESS);
        }
        return result(ExceptionMsg.FAILED);
    }
}