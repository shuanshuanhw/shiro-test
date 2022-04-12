package com.example.shirotest.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(@Qualifier("securityManager") DefaultWebSecurityManager securityManager){
        ShiroFilterFactoryBean  factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager);

        // 权限设置
        Map<String,String> map = new HashMap<>();
        map.put("/main","authc");
        map.put("/manage","perms[manage]");
        map.put("/administrator","roles[administrator]");
        //  通过这个map进行设置相对应的过滤器
        factoryBean.setFilterChainDefinitionMap(map);
        //  设置登录页面  发送这个请求 被视图解析器 捕获  跳转到login.html
        factoryBean.setLoginUrl("/login");
        //  设置未授权页面
        factoryBean.setUnauthorizedUrl("/unauth");
        return factoryBean;
    }



    @Bean
    public DefaultWebSecurityManager securityManager(@Qualifier("accountRealm") AccountRealm accountRealm){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(accountRealm);
        return manager;
    }



    @Bean
    public AccountRealm accountRealm(){
        return  new AccountRealm();
    }



    //用于thymeleaf模板使用shiro标签
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }

}
