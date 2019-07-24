package com.baizhi.controller;

import com.baizhi.dto.UserDto;
import com.baizhi.entity.Chapter;
import com.baizhi.service.ChapterService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/chapter")
@Slf4j
public class ChapterController {
    @Autowired
    ChapterService chapterService;
    @ResponseBody
    @RequestMapping("queryall")
    public UserDto queryall(int page, int rows,String aid){
        UserDto userDt=new UserDto();
        userDt.setPage(page);
        int counts=chapterService.count();
        userDt.setTotal(counts%rows==0?counts/rows:(counts/rows+1));
        userDt.setRecords(counts);
        Map<String,Object> map=new HashMap<>();
        map.put("pageNo",page);
        map.put("pageSize",rows);
        map.put("aid",aid);
        List<Chapter> limit = chapterService.limit(map);
        log.info("{}",limit);
        userDt.setRows(limit);
        return userDt;
    }
    @ResponseBody
    @RequestMapping("filedownload")
    public void filedownload(String fname, HttpServletResponse response, HttpServletRequest request) throws Exception{
        String realPath = request.getSession().getServletContext().getRealPath("/music");
        byte[] bs= FileCopyUtils.copyToByteArray(new File(realPath+"/"+fname));
        response.setHeader("content-disposition","attachment;filename="+ URLEncoder.encode(fname,"utf-8"));
        ServletOutputStream sos=response.getOutputStream();
        sos.write(bs);
    }
    @RequestMapping("sduq")
    @ResponseBody
    public String sduq(String oper, Chapter chapter){
        if (oper.equals("add")){
            String s = chapterService.save(chapter);
            return s;
        }
        return null;
    }
    @RequestMapping("upload")
    @ResponseBody
    public void upload(String id, MultipartFile downPath, HttpServletRequest request){
        String name = downPath.getOriginalFilename();
        double size = downPath.getSize()/1024/1024;
        String realPath = request.getSession().getServletContext().getRealPath("img");
        try {
            downPath.transferTo(new File(realPath,name));
            Map<String,Object> map=new HashMap<>();
            map.put("size",size);
            map.put("id",id);
            map.put("downPath",name);
            chapterService.updatePath(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
