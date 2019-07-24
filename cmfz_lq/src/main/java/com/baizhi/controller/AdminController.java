package com.baizhi.controller;


import com.baizhi.entity.Admin;
import com.baizhi.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
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
    public Map<String,Object> regist(String name, String password, HttpServletRequest request){
        Map<String, Object> regist = adminService.regist(name,password);
        if(regist.get("code").equals("200")){
            Admin admin = (Admin) regist.get("regist");
            HttpSession session = request.getSession();
            session.setAttribute("admin",admin);
        }
        return regist;
    }
    @RequestMapping("exit")
    public String exit(HttpServletRequest request){
        request.getSession().removeAttribute("admin");
        return "jsp/main";
    }
}
