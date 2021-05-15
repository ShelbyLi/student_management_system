package com.shelby.config;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;

import com.shelby.util.Jwt.JwtFilter;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.pam.AuthenticationStrategy;
import org.apache.shiro.authc.pam.FirstSuccessfulStrategy;
import org.apache.shiro.authc.pam.ModularRealmAuthenticator;
import org.apache.shiro.mgt.DefaultSessionStorageEvaluator;
import org.apache.shiro.mgt.DefaultSubjectDAO;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.mgt.SessionStorageEvaluator;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ShiroConfig {

    /**
     * 交由 Spring 来自动地管理 Shiro-Bean 的生命周期
     */
    @Bean
    public static LifecycleBeanPostProcessor getLifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 为 Spring-Bean 开启对 Shiro 注解的支持
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator() {
        DefaultAdvisorAutoProxyCreator app = new DefaultAdvisorAutoProxyCreator();
        app.setProxyTargetClass(true);
        return app;

    }

    /**
     * 不向 Spring容器中注册 JwtFilter Bean，防止 Spring 将 JwtFilter 注册为全局过滤器
     * 全局过滤器会对所有请求进行拦截，而本例中只需要拦截除 /login 和 /logout 外的请求
     * 另一种简单做法是：直接去掉 jwtFilter()上的 @Bean 注解
     */
    @Bean
    public FilterRegistrationBean<Filter> registration(JwtFilter filter) {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<Filter>(filter);
        registration.setEnabled(false);
        return registration;
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    /**
     * 配置访问资源需要的权限
     */
    @Bean
    ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSuccessUrl("/authorized");
        shiroFilterFactoryBean.setUnauthorizedUrl("/unauthorized");

        // 添加 jwt 专用过滤器，拦截除 /login 和 /logout 外的请求
        Map<String, Filter> filterMap = new LinkedHashMap<>();
        filterMap.put("jwtFilter", jwtFilter());
        shiroFilterFactoryBean.setFilters(filterMap);

        LinkedHashMap<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
        filterChainDefinitionMap.put("/login", "anon"); // 可匿名访问
        filterChainDefinitionMap.put("/register", "anon");
        filterChainDefinitionMap.put("/logout", "logout"); // 退出登录
        filterChainDefinitionMap.put("/**", "jwtFilter,authc"); // 需登录才能访问
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        return shiroFilterFactoryBean;
    }

    /**
     * 配置 ModularRealmAuthenticator
     */
    @Bean
    public ModularRealmAuthenticator authenticator() {
        ModularRealmAuthenticator authenticator = new MultiRealmAuthenticator();
        // 设置多 Realm的认证策略，默认 AtLeastOneSuccessfulStrategy
        AuthenticationStrategy strategy = new FirstSuccessfulStrategy();
        authenticator.setAuthenticationStrategy(strategy);
        return authenticator;
    }

    /**
     * 禁用session, 不保存用户登录状态。保证每次请求都重新认证
     */
    @Bean
    protected SessionStorageEvaluator sessionStorageEvaluator() {
        DefaultSessionStorageEvaluator sessionStorageEvaluator = new DefaultSessionStorageEvaluator();
        sessionStorageEvaluator.setSessionStorageEnabled(false);
        return sessionStorageEvaluator;
    }

    /**
     * JwtRealm 配置，需实现 Realm 接口
     */
    @Bean
    JwtRealm jwtRealm() {
        JwtRealm jwtRealm = new JwtRealm();
        // 设置加密算法
        CredentialsMatcher credentialsMatcher = new JwtCredentialsMatcher();
        // 设置加密次数
        jwtRealm.setCredentialsMatcher(credentialsMatcher);
        return jwtRealm;
    }

    /**
     * ShiroRealm 配置，需实现 Realm 接口
     */
    @Bean
    ShiroRealm shiroRealm() {
        ShiroRealm shiroRealm = new ShiroRealm();
        // 设置加密算法
        HashedCredentialsMatcher credentialsMatcher = new HashedCredentialsMatcher("SHA-1");
        // 设置加密次数
        credentialsMatcher.setHashIterations(16);
        shiroRealm.setCredentialsMatcher(credentialsMatcher);
        return shiroRealm;
    }

    /**
     * 配置 SecurityManager
     */
    @Bean
    public SecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 1.Authenticator
        securityManager.setAuthenticator(authenticator());

        // 2.Realm
        List<Realm> realms = new ArrayList<Realm>(16);
        realms.add(jwtRealm());
        realms.add(shiroRealm());
        securityManager.setRealms(realms);

        // 3.关闭shiro自带的session
        DefaultSubjectDAO subjectDAO = new DefaultSubjectDAO();
        subjectDAO.setSessionStorageEvaluator(sessionStorageEvaluator());
        securityManager.setSubjectDAO(subjectDAO);

        return securityManager;
    }
}

//
//import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
//import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
//import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
//import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
//import org.mapstruct.Qualifier;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
//
//import java.util.LinkedHashMap;
//import java.util.Map;
//import java.util.Properties;
//
//@Configuration
//public class ShiroConfig {
////    3 ShiroFilterFactoryBean
//    @Bean
////    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager defaultWebSecurityManager) {
//    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
//            ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
//        // 设置安全管理器
//        bean.setSecurityManager(getDefaultWebSecurityManager());
//        // 添加shiro的内置过滤器
//        /*
//        anon: 无需认证就能访问
//        authc: 认证才能访问
//        user: 拥有 记住我 功能才能用
//        perms: 拥有对某个资源的权限才能访问
//        role: 拥有某个角色权限才能访问
//         */
//        // 拦截
//        Map<String, String> filterMap = new LinkedHashMap<String, String>();
//        filterMap.put("/**", "authc");
////        filterMap.put("/student/*", "authc");
//
//        // 授权  未授权会跳转到未授权页面
//        filterMap.put("/student/add", "perms[student:add]");
//
//        // 设置登录的请求
//        bean.setLoginUrl("/toLogin");
//        // 设置未授权页面
//        bean.setUnauthorizedUrl("/noauth");
//        return bean;
//    }
////    2 DefaultWebSecurityFilter
//    @Bean(name = "securityManager")
////    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("shiroRealm") ShiroRealm shiroRealm) {
//    public DefaultWebSecurityManager getDefaultWebSecurityManager() {
//            DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
//        // 关联realm  要管理
//        securityManager.setRealm(shiroRealm());
//        return securityManager;
//    }
////    1 创建realm对象 需要自定义类
//    @Bean(name = "shiroRealm")
//    public ShiroRealm shiroRealm() {
//        return new ShiroRealm();
//    }
//
////    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("securityManager"))
////    @Bean
////    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
////        //shirFilter
////        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
////        shiroFilterFactoryBean.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
////        //拦截器.
////        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<String, String>();
////        // 配置不需要权限的资源
////        filterChainDefinitionMap.put("/static/**", "anon");
////        filterChainDefinitionMap.put("/index", "anon");
////        //配置退出过滤器,退出代码Shiro已经替我们实现
////        filterChainDefinitionMap.put("/logout", "logout");
////        //过滤链定义，从上向下顺序执行，/**放在最下边;
////        //<!-- authc:所有url都必须认证通过才可以访问; anon:所有url都都可以匿名访问-->
////        filterChainDefinitionMap.put("/**", "authc");
////        // 如果不设置默认会自动寻找Web工程根目录下的"/login"页面
////        shiroFilterFactoryBean.setLoginUrl("/login");
////        // 登录成功后要跳转的链接
////        shiroFilterFactoryBean.setSuccessUrl("/index");
////        //未授权界面;
////        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
////        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
////        return shiroFilterFactoryBean;
////    }
////
////    /**
////     * 凭证匹配器
////     * 密码校验交给Shiro的SimpleAuthenticationInfo进行处理
////     *
////     * @return
////     */
////    @Bean
////    public HashedCredentialsMatcher hashedCredentialsMatcher() {
////        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
////        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
////        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，md5(md5(""));
////        return hashedCredentialsMatcher;
////    }
////
////    @Bean
////    public ShiroRealm myShiroRealm() {
////        ShiroRealm myShiroRealm = new ShiroRealm();
////        myShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
////        return myShiroRealm;
////    }
////
////    @Bean
////    public DefaultWebSecurityManager securityManager() {
////        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
////        securityManager.setRealm(myShiroRealm());
////        return securityManager;
////    }
////
////    /**
////     * 开启shiro aop注解支持.
////     * 使用代理方式;需要开启代码支持;
////     *
////     * @param securityManager
////     * @return
////     */
////    @Bean
////    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
////        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
////        authorizationAttributeSourceAdvisor.setSecurityManager((org.apache.shiro.mgt.SecurityManager) securityManager);
////        return authorizationAttributeSourceAdvisor;
////    }
////
////    @Bean(name = "simpleMappingExceptionResolver")
////    public SimpleMappingExceptionResolver
////    createSimpleMappingExceptionResolver() {
////        SimpleMappingExceptionResolver r = new SimpleMappingExceptionResolver();
////        Properties mappings = new Properties();
////        mappings.setProperty("DatabaseException", "databaseError");//数据库异常处理
////        mappings.setProperty("UnauthorizedException", "403");
////        r.setExceptionMappings(mappings);
////        r.setDefaultErrorView("error");
////        r.setExceptionAttribute("ex");     // 缺省值"exception"
////        return r;
////    }
//}