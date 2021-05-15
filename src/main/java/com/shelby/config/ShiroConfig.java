package com.shelby.config;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.mapstruct.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

@Configuration
public class ShiroConfig {
//    3 ShiroFilterFactoryBean
    @Bean
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
            ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        bean.setSecurityManager(getDefaultWebSecurityManager());
        // 添加shiro的内置过滤器
        /*
        anon: 无需认证就能访问
        authc: 认证才能访问
        user: 拥有 记住我 功能才能用
        perms: 拥有对某个资源的权限才能访问
        role: 拥有某个角色权限才能访问
         */
        // 拦截
        Map<String, String> filterMap = new LinkedHashMap<String, String>();
        filterMap.put("/**", "authc");
//        filterMap.put("/student/*", "authc");

        // 授权  未授权会跳转到未授权页面
        filterMap.put("/student/add", "perms[student:add]");

        // 设置登录的请求
        bean.setLoginUrl("/toLogin");
        // 设置未授权页面
        bean.setUnauthorizedUrl("/noauth");
        return bean;
    }
//    2 DefaultWebSecurityFilter
    @Bean(name = "securityManager")
//    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm) {
    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
            DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 关联realm  要管理
        securityManager.setRealm(shiroRealm());
        return securityManager;
    }
//    1 创建realm对象 需要自定义类
    @Bean(name = "shiroRealm")
    public ShiroRealm shiroRealm() {
        return new ShiroRealm();
    }

//    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager"))
//    @Bean
//    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
//        //shirFilter
//        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        shiroFilterFactoryBean.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
//        //拦截器.
//        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
//        // 配置不需要权限的资源
//        filterChainDefinitionMap.put("/static/**", "anon");
//        filterChainDefinitionMap.put("/index", "anon");
//        //配置退出过滤器,退出代码Shiro已经替我们实现
//        filterChainDefinitionMap.put("/logout", "logout");
//        //过滤链定义，从上向下顺序执行，/**放在最下边;
//        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
//        filterChainDefinitionMap.put("/**", "authc");
//        // 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
//        shiroFilterFactoryBean.setLoginUrl("/login");
//        // 登录成功后要跳转的链接
//        shiroFilterFactoryBean.setSuccessUrl("/index");
//        //未授权界面;
//        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
//        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
//        return shiroFilterFactoryBean;
//    }
//
//    /**
//     * 凭证匹配器
//     * 密码校验交给Shiro的SimpleAuthenticationInfo进行处理
//     *
//     * @return
//     */
//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher() {
//        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
//        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
//        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，md5(md5(""));
//        return hashedCredentialsMatcher;
//    }
//
//    @Bean
//    public ShiroRealm myShiroRealm() {
//        ShiroRealm myShiroRealm = new ShiroRealm();
//        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
//        return myShiroRealm;
//    }
//
//    @Bean
//    public DefaultWebSecurityManager securityManager() {
//        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        securityManager.setRealm(myShiroRealm());
//        return securityManager;
//    }
//
//    /**
//     * 开启shiro aop注解支持.
//     * 使用代理方式;需要开启代码支持;
//     *
//     * @param securityManager
//     * @return
//     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//
//    @Bean(name = "simpleMappingExceptionResolver")
//    public SimpleMappingExceptionResolver
//    createSimpleMappingExceptionResolver() {
//        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
//        Properties mappings = new Properties();
//        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
//        mappings.setProperty("UnauthorizedException", "403");
//        r.setExceptionMappings(mappings);
//        r.setDefaultErrorView("error");
//        r.setExceptionAttribute("ex");     // 缺省值"exception"
//        return r;
//    }
}