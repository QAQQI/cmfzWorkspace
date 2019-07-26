package com.baizhi.conf;

import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroFilterConf {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean() {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        WebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        shiroFilterFactoryBean.setSecurityManager(webSecurityManager);
        return shiroFilterFactoryBean;
    }
}