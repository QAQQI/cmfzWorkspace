package com.baizhi.service;

import com.baizhi.Annotation.AopAnnocation;
import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class AdminServiceImpl implements AdminService{
    @Autowired
    AdminDAO adminDAO;

    @Override
    @AopAnnocation
    public Map<String,Object> regist(String name,String password) {
        Map<String,Object> map = new HashMap<>();
        Admin admin= adminDAO.regist(name);
        if(admin==null){
            map.put("code","300");
            map.put("message","用户名不存在");
            return map;
        }else if(admin.getPassword().equals(password)){
            map.put("code","200");
            map.put("message","登录成功");
            map.put("regist",admin);
            return map;
        }else {
            map.put("code","400");
            map.put("message","密码错误");
            return map;
        }
    }
}
