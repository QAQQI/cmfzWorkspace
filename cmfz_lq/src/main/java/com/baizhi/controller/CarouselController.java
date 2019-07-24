package com.baizhi.controller;

import com.baizhi.dto.UserDto;
import com.baizhi.entity.Carousel;
import com.baizhi.service.CarouselService;
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
@RequestMapping("/carousel")
@Slf4j
public class CarouselController {
    @Autowired
    CarouselService carouselService;

    @RequestMapping("queryAll")
    @ResponseBody
    public List<Carousel> quaryAll() {
        List<Carousel> carousels = carouselService.queryAll();
        return carousels;
    }

    @RequestMapping(value = "queryall")
    @ResponseBody
    public UserDto quserall(int page, int rows) {
        UserDto userDt = new UserDto();
        userDt.setPage(page);
        int counts = carouselService.count();
        userDt.setTotal(counts % rows == 0 ? counts / rows : (counts / rows + 1));
        userDt.setRecords(counts);
        Map<String, Integer> map = new HashMap<>();
        map.put("pageNo", page);
        map.put("pageSize", rows);
        List<Carousel> list = carouselService.limit(map);
        userDt.setRows(list);
        return userDt;
    }

    @RequestMapping(value = "sduq")
    @ResponseBody
    public String sduq(String oper, Carousel carousel) {
        log.info("{}000{}", oper, carousel);
        if (oper.equals("add")) {
            String s = carouselService.save(carousel);
            return s;
        } else if (oper.equals("del")) {
            String[] split = carousel.getId().split(",");
            carouselService.delete(split);
        } else if (oper.equals("edit")) {
            String s = carouselService.update(carousel);
            if (carousel.getImgPath()!="") {
                return s;
            }
        }
        return null;
    }

    @RequestMapping(value = "upload")
    @ResponseBody
    public void upload(String id, MultipartFile imgPath, HttpServletRequest request) {
        String name = imgPath.getOriginalFilename();
        String realPath = request.getSession().getServletContext().getRealPath("img");
        try {
            imgPath.transferTo(new File(realPath, name));
            Map<String, String> map = new HashMap<>();
            map.put("id", id);
            map.put("imgPath", name);
            carouselService.updatePath(map);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
