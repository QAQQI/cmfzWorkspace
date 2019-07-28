package com.baizhi.cmfz_lq;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.junit.Test;

public class MD5Test {
    @Test
    public void test(){
        Md5Hash jiaojiao = new Md5Hash("123456", "jiaojiao", 1024);
        String s = jiaojiao.toHex();
        System.out.println(s);
        System.out.println("liuqi");
    }
}
