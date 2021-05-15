package com.shelby.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import javax.persistence.*;

/**
 * @Author Shelby Li
 * @Date 2021/5/13 21:03
 * @Version 1.0
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Admin {
    @Id
    private int id;
    @Column(unique =true)
    private String name;
    @Column
    private String pwd;
    @Column
    private String perms;
//    @Id
//    @GeneratedValue
//    private Integer uid;
//    @Column(unique =true)
//    /**
//     * 账号
//     */
//    private String username;
//    /**
//     * 名称
//     */
//    private String name;
//    /**
//     * 密码
//     */
//    private String password;
//    /**
//     * 加密密码的盐
//     */
//    private String salt;
//    /**
//     * 用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.
//     */
//    private byte state;
//    /**
//     * 立即从数据库中进行加载数据;
//     */
//    @ManyToMany(fetch= FetchType.EAGER)//
//    @JoinTable(name = "SysUserRole", joinColumns = { @JoinColumn(name = "uid") }, inverseJoinColumns ={@JoinColumn(name = "roleId") })
//    /**
//     * 一个用户具有多个角色
//     */
//    private List<SysRole> roleList;//
//
//    /**
//     * 密码盐.
//     */
//    public String getCredentialsSalt(){
//
//        return this.username+this.salt;
//    }
//    //密码盐通过username+salt进行加密,具体可以看控制器中的添加用户功能
//    public String setCredentialsSalt(){
//        return this.username+this.salt;
//    }
}
