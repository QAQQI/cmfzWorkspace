package com.baizhi.service;

import com.baizhi.entity.Admin;

import java.util.Map;

public interface AdminService {
    public Map<String,Object> regist(String name,String password);
}
