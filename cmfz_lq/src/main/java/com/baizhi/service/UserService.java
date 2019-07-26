package com.baizhi.service;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {
    public List<User> limit(Map<String,Integer> map);
    public int count();
    public String save(User user);
    public void delete(String[] id);
    public String update(User user);
    public void updatePath(Map<String,String> map);
    public User queyOne(User user);
    public List<User> queryAll();
    public User queryByPhone(String phone);
    public User regist(User user);
    public String queryMothEcharts();
    public String queryProvinceEcharts();
}
