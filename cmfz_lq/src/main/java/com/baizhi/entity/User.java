package com.baizhi.entity;


import cn.afterturn.easypoi.excel.annotation.Excel;
import com.baizhi.Annotation.UserAnnotation;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.apache.ibatis.type.Alias;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

@Data
@Accessors(chain = true)
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {
    @UserAnnotation(name = "编号")
    @JsonProperty("userId")
    private String id;
    @Excel(name = "电话号码")
    @UserAnnotation(name = "电话号码")
    private String phone;
    @Excel(name = "密码")
    private String password;
    @Excel(name = "盐")
    private String salt;
    @Excel(name = "法名")
    private String dharmaName;
    @Excel(name = "省份")
    private String province;
    @Excel(name = "城市")
    private String city;
    @Excel(name = "性别")
    private String gender;
    @Excel(name = "个性签名")
    private String personalSign;
    @Excel(name = "头像",type = 2 ,width = 40 , height = 20)
    private String profile;
    @Excel(name = "状态")
    private String status;
    @Excel(name = "注册时间",databaseFormat = "yyyyMMddHHmmss", format = "yyyy-MM-dd")
    @UserAnnotation(name = "日期")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+8")
    private  Date registTime;
}
