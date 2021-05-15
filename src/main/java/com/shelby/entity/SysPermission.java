package com.shelby.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.List;

/**
 * @Author Shelby Li
 * @Date 2021/5/13 21:08
 * @Version 1.0
 */

@Entity
@Data
public class SysPermission {
    @Id
    @GeneratedValue
    /**
     * 主键
     */
    private Integer id;
    /**
     * 权限名称.
     */
    private String name;

    @Column(columnDefinition="enum('menu','button')")
    /**
     * 资源类型，[menu|button]
     */
    private String resourceType;
    /**
     * 资源路径
     */
    private String url;
    /**
     * 权限字符串
     */
    private String permission;
    // menu例子：role:*，
    // button例子：role:create,role:update,role:delete,role:view
    /**
     * 父编号
     */
    private Long parentId;
    /**
     * 父编号列表
     */
    private String parentIds;
    private Boolean available = Boolean.FALSE;
    @ManyToMany
    @JoinTable(name="SysRolePermission",joinColumns={@JoinColumn(name="permissionId")},inverseJoinColumns={@JoinColumn(name="roleId")})
    private List<SysRole> roles;
}
