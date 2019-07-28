package com.baizhi.conf;

import com.baizhi.utils.Myrealm;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class ShiroFilterConf {
    @Bean
    public ShiroFilterFactoryBean getShiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
//        WebSecurityManager webSecurityManager = new DefaultWebSecurityManager();
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        Map<String, String> map = new HashMap<>();
        map.put("/**", "authc");
        map.put("/boot/**", "anon");
        map.put("/echarts/**", "anon");
        map.put("/img/**", "anon");
        map.put("/jqgrid/**", "anon");
        map.put("/kindeditor/**", "anon");
        map.put("/music/**", "anon");
        map.put("/admin/regist", "anon");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(map);
        shiroFilterFactoryBean.setLoginUrl("/jsp/main.jsp");
        return shiroFilterFactoryBean;
    }

    @Bean
    public SecurityManager getSecurityManager(Myrealm myrealm, org.apache.shiro.cache.CacheManager cacheManager) {
        DefaultSecurityManager defaultSecurityManager = new DefaultWebSecurityManager();
        defaultSecurityManager.setRealm(myrealm);
        defaultSecurityManager.setCacheManager(cacheManager);
        return defaultSecurityManager;
    }

    @Bean
    public Myrealm getMyrealm(CredentialsMatcher credentialsMatcher) {
        Myrealm myrealm = new Myrealm();
        myrealm.setCredentialsMatcher(credentialsMatcher);
        return myrealm;
    }

    @Bean
    public CredentialsMatcher getCredentialsMatcher() {
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("MD5");
        hashedCredentialsMatcher.setHashIterations(1024);
        return hashedCredentialsMatcher;
    }
    @Bean
    public CacheManager getCache(){
        return new EhCacheManager();
    }
}