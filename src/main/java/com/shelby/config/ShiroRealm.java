package com.shelby.config;

import com.shelby.entity.Admin;
import com.shelby.entity.SysPermission;
import com.shelby.entity.SysRole;
import com.shelby.mapper.AdminMapper;
import com.shelby.service.AdminService;
import org.apache.catalina.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;

public class ShiroRealm extends AuthorizingRealm {
    @Autowired
    AdminService adminService;
//    授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("执行了授权");
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission("user:add");

        Subject subject = SecurityUtils.getSubject();
        Admin currentAdmin = (Admin) subject.getPrincipal();
        info.addStringPermission(currentAdmin.getPerms());
        return info;
    }

//    认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("执行了认证");

        UsernamePasswordToken userToken = (UsernamePasswordToken) token;
        // 连接数据库
        Admin admin = adminService.queryByUsername(userToken.getUsername());
        if (admin == null) {
            return null;  // null即抛出异常 UnknownAccountException
        } else if  (!userToken.getUsername().equals(admin.getName())) {
            return null;  // 抛出异常 UnknownAccountException
        }

        return new SimpleAuthenticationInfo("", admin.getPwd(), "");
    }

//    @Resource
//    private AdminMapper adminMapper;
//
//    @Override
//    /**
//     * 权限配置
//     */
//    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        //拿到用户信息
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        Admin adminInfo = (Admin) principals.getPrimaryPrincipal();
//
//        for (SysRole role : adminInfo.getRoleList()) {
//            //将角色放入SimpleAuthorizationInfo
//            info.addRole(role.getRole());
//            //用户拥有的权限
//            for (SysPermission p : role.getPermissions()) {
//                info.addStringPermission(p.getPermission());
//            }
//        }
//        return info;
//    }
//
//    /**
//     * 进行身份认证,判断用户名密码是否匹配正确
//     */
//    @Override
//    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token)
//            throws AuthenticationException {
//        //获取用户的输入的账号
//        String username = (String) token.getPrincipal();
//        System.out.println(token.getCredentials());
//        //通过username从数据库中查找 User对象，如果找到，没找到.
//        //Shiro有时间间隔机制，2分钟内不会重复执行该方法
//        //获取用户信息
//        Admin adminInfo = adminMapper.findByUsername(username);
//
//        if (adminInfo == null) {
//            return null;
//        }
//        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(
//                /**
//                 * 用户名
//                 */
//                adminInfo,
//                /**
//                 * 密码
//                 */
//                adminInfo.getPassword(),
//                ByteSource.Util.bytes(adminInfo.getCredentialsSalt()),
//                /**
//                 * realm name
//                 */
//                getName()
//        );
//        return info;
//    }

}