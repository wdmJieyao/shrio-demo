package com.example.shirospringboot.config;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.authc.credential.Md5CredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 自定义Shiro配置
 * 1、注入自定义realm对象，需要自定义
 * 2、注入DefaultWebSecurityManager
 * 3、注入ShiroFilterFactoryBean
 */
@Configuration
public class ShiroConfig {

    /**
     * 过滤管理器
     */
    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(@Qualifier("customSecurityManager") DefaultWebSecurityManager manager) {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        // 设置安全管理器
        factoryBean.setSecurityManager(manager);

        /*
         * Shiro内置过滤器
         * anon: 无需认证就可以访问
         * authc: 必须认证才可访问
         * user: 必须拥有“记住我”才可以访问
         * perms: 拥有对应的权限才可访问
         * role: 拥有某个角色权限才能访问
         */
        Map<String, String> filterMap = new LinkedHashMap<>(16);
        filterMap.put("/user/update", "authc");
        // 授权
        filterMap.put("/user/add", "perms[addUser]");
        factoryBean.setFilterChainDefinitionMap(filterMap);
        // 设置登录页面
        factoryBean.setLoginUrl("/user/toLogin");
        // 设置未授权页面
        // factoryBean.setUnauthorizedUrl("xxx");
        return factoryBean;
    }

    @Bean(name = "customSecurityManager")
    public DefaultWebSecurityManager getDefaultWebSecurityManager(@Qualifier("UserRealm") UserRealm userRealm) {
        DefaultWebSecurityManager customSecurityManager = new DefaultWebSecurityManager();
        /* 作为中间商  需要管理Realm*/
        customSecurityManager.setRealm(userRealm);
        return customSecurityManager;
    }

    @Bean(name = "UserRealm")
    public UserRealm getUserRealm() {
        UserRealm realm = new UserRealm();
        Md5CredentialsMatcher matcher = new Md5CredentialsMatcher();
        realm.setCredentialsMatcher(matcher);
        return realm;
    }

    @Bean(name = "shiroDialect")
    public ShiroDialect getShiroDialect() {
        return new ShiroDialect();
    }
}
