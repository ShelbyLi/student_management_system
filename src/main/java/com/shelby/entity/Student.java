package com.shelby.entity;

import lombok.Data;
import org.apache.tomcat.jni.User;

import javax.persistence.Column;
import javax.persistence.Id;

/**
 * @Author Shelby Li
 * @Date 2021/4/21 14:31
 * @Version 1.0
 */

@Data
public class Student extends UserEntity {
//    @Id
//    private int id;
//    @Column
//    private String name;
    @Id
    protected Long id; // 主键ID
    @Column
    protected String name; // 登录用户名
    @Column
    protected String password; // 登录密码
    @Column
    protected String nickName; // 昵称
    @Column
    protected Boolean locked; // 账户是否被锁定
    @Column
    private int age;
    @Column
    private int gpa;

    private Resume resume;
}
