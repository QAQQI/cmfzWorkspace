package com.baizhi.controller;

import com.baizhi.dto.UserDto;
import com.baizhi.entity.Album;
import com.baizhi.entity.Article;
import com.baizhi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;
//@RestController
@Controller
@RequestMapping(value = "/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;
    @ResponseBody
    @RequestMapping("queryall")
    public UserDto queryall(int page, int rows){
        UserDto userDt=new UserDto();
        userDt.setPage(page);
        int counts=articleService.count();
        userDt.setTotal(counts%rows==0?counts/rows:(counts/rows+1));
        userDt.setRecords(counts);
        Map<String,Integer> map=new HashMap<>();
        map.put("pageNo",page);
        map.put("pageSize",rows);
        List<Article> limit = articleService.limit(map);
        userDt.setRows(limit);
        return userDt;
    }
    @RequestMapping("sduq")
    @ResponseBody
    public String sduq(String oper, Article article){
       if(oper.equals("del")){
            String[] split = article.getId().split(",");
            articleService.delete(split);
        }else if(oper.equals("add")){
           articleService.save(article);
       }
        return null;
    }
    @ResponseBody
    @RequestMapping("upload")
    public Map<String , Object> upload(HttpServletRequest request, MultipartFile file){
        String name = file.getOriginalFilename();
        String realPath = request.getSession().getServletContext().getRealPath("img");
        Map<String , Object> map = new HashMap<>();

        try {
            file.transferTo(new File(realPath,name));
            map.put("error",0);
            map.put("url","http://localhost:8989/cmfz/img/"+name);
        } catch (IOException e) {
            e.printStackTrace();
            map.put("error",1);
            map.put("url","http://localhost:8989/cmfz/img/"+name);
        }

        return map;
    }
    @RequestMapping("showAll")
    @ResponseBody
    public Map<String , Object> showAll(HttpServletRequest request){
        System.out.println("11111");
        String articlePic = request.getSession().getServletContext().getRealPath("img");
        File file = new File(articlePic);
        String[] list = file.list();
        Map<String , Object> map = new HashMap<>();
        map.put("current_url","http://localhost:8989/cmfz/img/");
        map.put("total_count",list.length);
        List<Object> lists = new ArrayList<>();
        for (int i = 0; i < list.length; i++) {
            String s = list[i];
            Map<String , Object> map1 = new HashMap<>();
            map1.put("is_dir",false);
            map1.put("has_file",false);
            File file1 = new File(articlePic,s);
            long length = file1.length();
            map1.put("filesize",length);
            map1.put("is_photo",true);
            String substring = s.substring(s.lastIndexOf(".") + 1);
            map1.put("filetype",substring);
            map1.put("filename",s);
            map1.put("datetime",new Date());
            lists.add(map1);
        }
        map.put("file_list",lists);
        System.out.println("11111");
        return map;
    }
}
