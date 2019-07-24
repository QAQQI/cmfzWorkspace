package com.baizhi.dao;

import com.baizhi.entity.User;

import java.util.List;
import java.util.Map;

public interface UserDAO {
    public List<User> limit(Map<String,Integer> map);
    public int count();
    public void save(User user);
    public void delete(String[] ids);
    public void update(User user);
    public void updatePath(Map<String,String> map);
    public User queryOne(User user);
    public List<User> queryAll();
    public User queryByPhone(String phone);
    public List<Map<String,Object>> queryMothEcharts();
    public List<Map<String,Object>> queryProvinceEcharts();
}
