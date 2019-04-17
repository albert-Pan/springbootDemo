package com.pw.example.demo4.config.shiro;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Administrator on 2019/3/18 0018.
 */
@Configuration
public class ShiroConfig {

    private final static Logger logger = LoggerFactory.getLogger(ShiroConfig.class);

    /**
     * 配置shiro过滤器
     * @author zhengkai
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(DefaultWebSecurityManager  securityManager) {
        //定义shiroFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean=new ShiroFilterFactoryBean();
        // 设置securityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        //LinkedHashMap是有序的，进行顺序拦截器配置
        Map<String,String> filterChainMap = new LinkedHashMap<String, String>();
        //配置logout过滤器

        filterChainMap.put("/user/logout", "logout");

        //登录为白名单
        filterChainMap.put("/user/login","anon");

        //所有url必须通过认证才可以访问
        filterChainMap.put("/**","authc");

        //设置成功之后要跳转的链接
        //shiroFilterFactoryBean.setSuccessUrl("/index");
        //设置未授权界面
        //shiroFilterFactoryBean.setUnauthorizedUrl("/403");

        //设置默认登录的url,主要作用是访问未经授权的提示需要登陆
        shiroFilterFactoryBean.setLoginUrl("/user/unauth");

        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/user/notRole");

        //设置shiroFilterFactoryBean的FilterChainDefinitionMap
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainMap);
        return shiroFilterFactoryBean;
    }


    /**
     * 配置安全管理器
     * @author zhengkai
     */
    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        securityManager.setRealm(getSysShiroRealm());
        securityManager.setSessionManager(sessionManager());
        return securityManager;
    }

    @Bean
    public RedisSessionDao getRedisSessionDAO() {
        RedisSessionDao redisSessionDao=new RedisSessionDao();
        logger.info("sessionExpireTime={}",redisSessionDao.getExpireTime());
        //redisSessionDao.setExpireTime(sessionExpireTime);
        return redisSessionDao;
    }

    //自定义sessionManager
    @Bean
    public SessionManager sessionManager() {
        SysSessionManager mySessionManager = new SysSessionManager();
        mySessionManager.setSessionDAO(getRedisSessionDAO());
        mySessionManager.setSessionValidationSchedulerEnabled(true);
        mySessionManager.setDeleteInvalidSessions(true);
        mySessionManager.setSessionIdCookie(getSessionIdCookie());
        return mySessionManager;
    }

    @Bean(name="sessionIdCookie")
    public SimpleCookie getSessionIdCookie() {
        SimpleCookie simpleCookie = new SimpleCookie("sessionId");
        return simpleCookie;
    }

    @Bean(name = "sysShiroRealm")
    public SysShiroRealm getSysShiroRealm(){
        SysShiroRealm sysShiroRealm = new SysShiroRealm();
        sysShiroRealm.setCredentialsMatcher(hashedCredentialsMatcher());
        return sysShiroRealm;
    }

    /**
     * 注册全局异常处理
     * @return
     */
    @Bean(name = "exceptionResolver")
    public HandlerExceptionResolver handlerExceptionResolver() {
        return new SysExceptionHandler();
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));
        return hashedCredentialsMatcher;
    }


    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }
    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
        return authorizationAttributeSourceAdvisor;
    }


}

