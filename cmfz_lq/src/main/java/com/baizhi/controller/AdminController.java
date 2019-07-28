package com.baizhi.controller;


import com.baizhi.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {
    @Autowired
    AdminService adminService;

    @ResponseBody
    @RequestMapping("regist")
    public Map<String, Object> regist(String name, String password, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(name, password);
        try {
            subject.login(token);
            map.put("code","200");
            map.put("message","登录成功");
        } catch (UnknownAccountException e) {
            map.put("code","300");
            map.put("message","用户名不存在");
        } catch (IncorrectCredentialsException e) {
            map.put("code","400");
            map.put("message","密码错误");
        }
        log.info("{}",map);
        return map;
    }

    @RequestMapping("exit")
    public String exit(HttpServletRequest request) {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        return "jsp/main";
    }
}
