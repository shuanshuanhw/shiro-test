package com.example.shirotest.shiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.session.SessionListener;
import org.apache.shiro.session.mgt.SimpleSessionFactory;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroConfig {


    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(){
        ShiroFilterFactoryBean  factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager());

        // 权限设置
        Map<String,String> map = new HashMap<>();
        map.put("/","authc");
        map.put("/main","anon");
        map.put("/main1","authc");
//        map.put("/main","authc");
     //   map.put("/main","");
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
    public DefaultWebSecurityManager securityManager(){
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(accountRealm());
        manager.setSessionManager(sessionManager());
        return manager;
    }



   // @Bean
    @Bean
    public AccountRealm accountRealm(){
        return  new AccountRealm();
    }



    //用于thymeleaf模板使用shiro标签
    @Bean
    public ShiroDialect shiroDialect(){
        return new ShiroDialect();
    }


    /**
     * 会话管理器
     */
    int expireTime = 60;
    @Bean
    public DefaultWebSessionManager sessionManager()
    {
        DefaultWebSessionManager manager = new DefaultWebSessionManager();

        Collection<SessionListener> listeners = new ArrayList<SessionListener>();
        //配置监听
        listeners.add(new ShiroSessionListener());
        manager.setSessionListeners(listeners);

        manager.setSessionIdCookie(sessionIdCookie());
        // 加入缓存管理器
        manager.setCacheManager(new EhCacheManager());
        // 删除过期的session
        manager.setDeleteInvalidSessions(true);
        // 设置全局session超时时间
        manager.setGlobalSessionTimeout(expireTime * 60 * 1000);
        // 去掉 JSESSIONID
//        manager.setSessionIdUrlRewritingEnabled(true);
        manager.setSessionIdUrlRewritingEnabled(false);
        // 定义要使用的无效的Session定时调度器
     //   manager.setSessionValidationScheduler(SpringUtils.getBean(SpringSessionValidationScheduler.class));
        // 是否定时检查session
        manager.setSessionValidationSchedulerEnabled(true);
        // 自定义SessionDao
        manager.setSessionDAO(sessionDAO());
        // 自定义sessionFactory
        manager.setSessionFactory(new SimpleSessionFactory());
        return manager;
    }


    // ehcache
    @Bean
    public SessionDAO sessionDAO() {
        EnterpriseCacheSessionDAO enterpriseCacheSessionDAO = new EnterpriseCacheSessionDAO();
        enterpriseCacheSessionDAO.setActiveSessionsCacheName("shiro-activeSessionCache");
        enterpriseCacheSessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        return enterpriseCacheSessionDAO;
    }

    @Bean("sessionIdCookie")
    public SimpleCookie sessionIdCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("sid");
        simpleCookie.setHttpOnly(true);
        simpleCookie.setPath("/");
        //maxAge=-1表示浏览器关闭时失效此Cookie
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }

    /**
     * cookie对象;
     * @return
     */
 //   @Bean(name="rememberMeCookie")
    public SimpleCookie rememberMeCookie(){
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //cookie生效时间30天,单位秒;
        simpleCookie.setMaxAge(2592000);
        return simpleCookie;
    }

    /**
     * cookie管理对象;记住我功能
     * @return
     */
  //  @Bean
    public CookieRememberMeManager rememberMeManager(){
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        // cookieRememberMeManager.setCipherKey用来设置加密的Key,参数类型byte[],字节数组长度要求16
        // cookieRememberMeManager.setCipherKey(Base64.decode("3AvVhmFLUs0KTA3Kprsdag=="));
        cookieRememberMeManager.setCipherKey("ZHANGXIAOHEI_CAT".getBytes());
        return cookieRememberMeManager;
    }


}
