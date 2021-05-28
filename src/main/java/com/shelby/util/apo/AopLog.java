package com.shelby.util.apo;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author Shelby Li
 * @Date 2021/4/8 13:46
 * @Version 1.0
 */

@Aspect
@Component
@Slf4j
public class AopLog {
//    private Logger logger = LoggerFactory.getLogger(this.getClass());
    ThreadLocal<Long> startTime = new ThreadLocal<>();

    @Pointcut("execution(* com.shelby.controller..*.*(..))")
    public void aopWebLog() {

    }

    @Before("aopWebLog()")
    public void doBefore(JoinPoint joinPoint) throws Throwable {
        startTime.set(System.currentTimeMillis());
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        log.info("URL : " + request.getRequestURL().toString());
        log.info("HTTP方法 : " + request.getMethod());
        log.info("IP地址 : " + request.getRemoteAddr());
        log.info("类的方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
        log.info("参数 : " + request.getQueryString());
    }

    @AfterReturning(pointcut = "aopWebLog()",returning = "retObject")
    public void doAfterReturning(Object retObject) throws Throwable {
        // 处理完请求，返回内容
        log.info("应答值 : " + retObject);
        log.info("费时: " + (System.currentTimeMillis() - startTime.get()));
    }

    //抛出异常后通知（After throwing advice） ： 在方法抛出异常退出时执行的通知。
    @AfterThrowing(pointcut = "aopWebLog()", throwing = "ex")
    public void addAfterThrowingLogger(JoinPoint joinPoint, Exception ex) {
        log.error("执行 " + " 异常", ex);
    }

}
