package com.baizhi.dao;

import com.baizhi.entity.Admin;

public interface AdminDAO {
    public Admin regist(String name);
    public Admin selectAdmin(String username);
}
