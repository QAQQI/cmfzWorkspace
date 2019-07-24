package com.baizhi.controller;

import com.baizhi.dto.UserDto;
import com.baizhi.entity.Album;
import com.baizhi.service.AlbumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/album")
@Slf4j
public class AlbumController {
    @Autowired
    AlbumService albumService;

    @ResponseBody
    @RequestMapping("queryall")
    public UserDto queryall(int page,int rows){
        UserDto userDt=new UserDto();
        userDt.setPage(page);
        int counts=albumService.count();
        userDt.setTotal(counts%rows==0?counts/rows:(counts/rows+1));
        userDt.setRecords(counts);
        Map<String,Integer> map=new HashMap<>();
        map.put("pageNo",page);
        map.put("pageSize",rows);
        List<Album> limit = albumService.limit(map);
        userDt.setRows(limit);
        return userDt;
    }
    @RequestMapping("sduq")
    @ResponseBody
    public String sduq(String oper, Album album){
        if (oper.equals("add")){
            String s = albumService.save(album);
            return s;
        }else if(oper.equals("del")){
            String[] split = album.getId().split(",");
            albumService.delete(split);
        }else if(oper.equals("edit")){
            String s = albumService.update(album);
            if (album.getCover()!="") {
                return s;
            }
        }
        return null;
    }
    @RequestMapping("upload")
    @ResponseBody
    public void upload(String id, MultipartFile cover, HttpServletRequest request){
        String name = cover.getOriginalFilename();
        String realPath = request.getSession().getServletContext().getRealPath("img");
        try {
            cover.transferTo(new File(realPath,name));
            Map<String,String> map=new HashMap<>();
            map.put("id",id);
            map.put("cover",name);
            albumService.updatePath(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
