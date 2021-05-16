package com.example.shirospringboot.config;


import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Configuration;
import org.thymeleaf.util.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Realm 数据处理权限
 */
@Configuration
@Slf4j
public class UserRealm extends AuthorizingRealm {
    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        log.info("UserRealm..doGetAuthorizationInfo..执行了自定义授权操作！");
        Subject currentUser = SecurityUtils.getSubject();
        Map<String,String> principal = (Map<String, String>) currentUser.getPrincipal();
        // 授权
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addStringPermission(principal.get("perms"));
        return info;
    }

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        log.info("UserRealm..doGetAuthenticationInfo..执行了自定义认证操作！");
        // todo 数据库中获取用户数据
        // ...


        // 伪造用户数据
        String name = "root";
        String password = "21232f297a57a5a743894a0e4a801fc3";
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 账户认证
        if (!StringUtils.equalsIgnoreCase(name, token.getUsername())) {
            return null;
        }


        HashMap<String, String> map = new HashMap<>();
        map.put("name", name);
        map.put("password", password);
        map.put("perms", "addUser");
        // 密码认证由Shiro框架来做
        return new SimpleAuthenticationInfo(map, password, "");
    }


}
