package com.baizhi.utils;

import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import com.baizhi.entity.Authority;
import com.baizhi.entity.Role;
import com.sun.scenario.effect.impl.prism.PrRenderInfo;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.util.List;

public class Myrealm extends AuthorizingRealm{
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("进行授权");
        String username = (String)principalCollection.getPrimaryPrincipal();
        AdminDAO adminDAO = (AdminDAO) SpringContextUtil.getBean(AdminDAO.class);
        Admin regist = adminDAO.selectAdmin(username);
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<Role> roles = regist.getRoles();
        for (Role role : roles) {
            simpleAuthorizationInfo.addRole(role.getRoleName());
            List<Authority> authorities = role.getAuthorities();
            for (Authority authority : authorities) {
                simpleAuthorizationInfo.addStringPermission(authority.getAuthorityName());
            }
        }
        return simpleAuthorizationInfo;
    }//AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("进行认证");
        AdminDAO adminDAO = (AdminDAO) SpringContextUtil.getBean(AdminDAO.class);
        String username = (String) authenticationToken.getPrincipal();
        Admin regist = adminDAO.regist(username);
        if (regist == null) {
            return null;
        } else {
            SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(username, regist.getPassword(), ByteSource.Util.bytes(regist.getSalt()),this.getName());
            return simpleAuthenticationInfo;
        }
    }
}
