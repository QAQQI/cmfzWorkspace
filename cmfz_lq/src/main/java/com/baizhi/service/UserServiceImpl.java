package com.baizhi.service;

import com.baizhi.Annotation.AopAnnocation;
import com.baizhi.dao.UserDAO;
import com.baizhi.entity.Echarts;
import com.baizhi.entity.User;
import com.baizhi.utils.MD5Utils;
import com.google.gson.Gson;
import io.goeasy.GoEasy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
public class UserServiceImpl implements UserService {
    @Autowired
    UserDAO userDAO;
    @Override
    @AopAnnocation
    public List<User> limit(Map<String, Integer> map) {
        int pageNo = map.get("pageNo");
        int pageSize = map.get("pageSize");
        pageNo = (pageNo - 1) * pageSize;
        map.put("pageNo", pageNo);
        List<User> limit = userDAO.limit(map);
        return limit;
    }
    @Transactional
    @Override
    public String save(User user) {
        UUID uuid = UUID.randomUUID();
        String uu = uuid.toString();
        user.setId(uu);
        String salt = MD5Utils.getSalt();
        String password = MD5Utils.getPassword(user.getPassword()+salt);
        user.setSalt(salt);
        user.setPassword(password);
        userDAO.save(user);
        return uu;
    }
    @Override
    @AopAnnocation
    public String queryProvinceEcharts() {
        List<Map<String, Object>> maps = userDAO.queryProvinceEcharts();
        List<Echarts> list1 = new ArrayList<>();
        List<Echarts> list2 = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            Echarts echarts = new Echarts();
            if (map != null) {
                echarts.setName((String) map.get("province"));
                echarts.setValue(Integer.parseInt(map.get("count").toString()));
                if ("m".equals(map.get("gender"))) {
                    list1.add(echarts);
                } else {
                    list2.add(echarts);
                }
            }
        }
        Map<String, List<Echarts>> map = new HashMap<>();
        map.put("m", list1);
        map.put("w", list2);
        Gson gson = new Gson();
        String s = gson.toJson(map);
        GoEasy goEasy = new GoEasy("https://rest-hangzhou.goeasy.io", "BC-8f3be71a181546e1b56e77b861b5e85a");
        goEasy.publish("demo_channel0", s);
        return s;
    }

    @Override
    @AopAnnocation
    public String queryMothEcharts() {
        List<Map<String, Object>> maps = userDAO.queryMothEcharts();
        List<String> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (Map<String, Object> map : maps) {
            list1.add(((String) map.get("month")).split("-")[1]);
            list2.add(Integer.parseInt(map.get("count").toString()));
        }
        Map<String, Object> map = new HashMap<>();
        map.put("month", list1);
        map.put("count", list2);
        Gson gson = new Gson();
        String s = gson.toJson(map);
        GoEasy goEasy = new GoEasy("https://rest-hangzhou.goeasy.io", "BC-8f3be71a181546e1b56e77b861b5e85a");
        goEasy.publish("demo_channel", s);
        return s;
    }

    @Override
    @AopAnnocation
    public User regist(User user) {
        UUID uuid = UUID.randomUUID();
        String uu = uuid.toString();
        user.setId(uu);
        String salt = MD5Utils.getSalt();
        String password = MD5Utils.getPassword(user.getPassword()+salt);
        user.setSalt(salt);
        user.setPassword(password);
        user.setRegistTime(new Date());
        userDAO.save(user);
        return user;
    }

    @Transactional
    @Override
    public void delete(String[] id) {
        userDAO.delete(id);
    }

    @Override
    @AopAnnocation
    public List<User> queryAll() {
        List<User> users = userDAO.queryAll();
        return users;
    }

    @Override
    @AopAnnocation
    public User queryByPhone(String phone) {
        User user = userDAO.queryByPhone(phone);
        return user;
    }

    @Override
    @AopAnnocation
    public User queyOne(User user) {
        User user1 = userDAO.queryOne(user);
        return user1;
    }

    @Transactional
    @Override
    public String update(User user) {
        User user1 = queyOne(user);
        if(user1==null){
            String salt = MD5Utils.getSalt();
            String password = MD5Utils.getPassword(user.getPassword()+salt);
            user.setSalt(salt);
            user.setPassword(password);
        }else {
            user.setSalt(user1.getSalt());
        }
        userDAO.update(user);
        return user.getId();
    }
    @Transactional
    @Override
    public void updatePath(Map<String, String> map) {
        userDAO.updatePath(map);
    }

    @Override
    @AopAnnocation
    public int count() {
        return userDAO.count();
    }
}
