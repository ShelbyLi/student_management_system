package com.shelby.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * @Author Shelby Li
 * @Date 2021/5/15 19:45
 * @Version 1.0
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserEntity implements Serializable {


    private static final long serialVersionUID = 1L;
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
}
