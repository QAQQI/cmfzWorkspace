package com.baizhi.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.dto.UserDto;
import com.baizhi.entity.User;
import com.baizhi.service.UserService;
import com.baizhi.utils.MD5Utils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/user")
public class UserController {
    @Autowired
    UserService userService;

    @ResponseBody
    @RequestMapping("queryall")
    public UserDto queryall(int page, int rows) {
        UserDto userDt = new UserDto();
        userDt.setPage(page);
        int counts = userService.count();
        userDt.setTotal(counts % rows == 0 ? counts / rows : (counts / rows + 1));
        userDt.setRecords(counts);
        Map<String, Integer> map = new HashMap<>();
        map.put("pageNo", page);
        map.put("pageSize", rows);
        List<User> limit = userService.limit(map);
        userDt.setRows(limit);
        return userDt;
    }

    @RequestMapping("sduq")
    @ResponseBody
    public String sduq(String oper, User user) {
        if (oper.equals("add")) {
            String s = userService.save(user);
            return s;
        } else if (oper.equals("del")) {
            String[] split = user.getId().split(",");
            userService.delete(split);
        } else if (oper.equals("edit")) {
            String s = userService.update(user);
            if (user.getProfile() != "") {
                return s;
            }
        }
        return null;
    }

    @RequestMapping("upload")
    @ResponseBody
    public void upload(String id, MultipartFile profile, HttpServletRequest request) {
        String name = profile.getOriginalFilename();
        if (name.equals("")) {
            return;
        }
        String realPath = request.getSession().getServletContext().getRealPath("img");
        try {
            profile.transferTo(new File(realPath, name));
            Map<String, String> map = new HashMap<>();
            Set<String> strings = map.keySet();
            map.put("id", id);
            map.put("profile", name);
            userService.updatePath(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("uploadex")
    public void uploadex(HttpServletResponse response, HttpServletRequest request) throws Exception {
        String name = "user.xls";
        String realPath = request.getSession().getServletContext().getRealPath("img");
        List<User> users = userService.queryAll();
        for (User user : users) {
            String profile = user.getProfile();
            user.setProfile(realPath + "/" + profile);
        }
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户", "学生"),
                User.class, users);
        workbook.write(new FileOutputStream(realPath + "/" + name));
        workbook.close();
        byte[] bs = FileCopyUtils.copyToByteArray(new File(realPath + "/" + name));
        response.setHeader("content-disposition", "attachment;filename=" + URLEncoder.encode(name, "utf-8"));
        ServletOutputStream sos = response.getOutputStream();
        sos.write(bs);
    }

    @RequestMapping("uploadxls")
    @ResponseBody
    public void uploadxls(MultipartFile file, HttpServletRequest request) throws Exception {
        System.out.println(file);
        Workbook workbook = new HSSFWorkbook(file.getInputStream());
        Sheet sheetAt = workbook.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        for (int i = 0; i < lastRowNum - 1; i++) {
            User user = new User();
            Row row = sheetAt.getRow(i + 2);
            Cell cell = row.getCell(0);
            user.setPhone(cell.getStringCellValue());
            System.out.println(cell.getStringCellValue());
            Cell cell1 = row.getCell(1);
            user.setPassword(cell1.getStringCellValue());
            Cell cell2 = row.getCell(2);
            user.setSalt(cell2.getStringCellValue());
            Cell cell3 = row.getCell(3);
            user.setDharmaName(cell3.getStringCellValue());
            Cell cell4 = row.getCell(4);
            user.setProvince(cell4.getStringCellValue());
            Cell cell5 = row.getCell(5);
            user.setCity(cell5.getStringCellValue());
            Cell cell6 = row.getCell(6);
            user.setGender(cell6.getStringCellValue());
            Cell cell7 = row.getCell(7);
            user.setPersonalSign(cell7.getStringCellValue());
            Cell cell8 = row.getCell(8);
            user.setProfile(cell8.getStringCellValue());
            Cell cell9 = row.getCell(9);
            user.setStatus(cell9.getStringCellValue());
            Cell cell10 = row.getCell(10);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String stringCellValue = cell10.getStringCellValue();
            user.setRegistTime(simpleDateFormat.parse(stringCellValue));
            UUID uuid = UUID.randomUUID();
            String uu = uuid.toString();
            user.setId(uu);
            userService.save(user);
        }
    }

    @RequestMapping("login")
    @ResponseBody
    public Object login(User user, String code) {
        Map<String, Object> map = new HashMap<>();
        User user1 = userService.queryByPhone(user.getPhone());
        if (user1 == null) {
            map.put("error", -200);
            map.put("message", "用户名不存在");
        }else {
            if(!MD5Utils.getPassword(user.getPassword() + user1.getSalt()).equals(user1.getPassword())){
                map.put("error", -300);
                map.put("message", "密码错误");
            }else {
                return user1;
            }
        }
        return map;
    }

    @RequestMapping("regist")
    @ResponseBody
    public Object regist(User user, MultipartFile file, HttpServletRequest request) {
        User user1 = userService.queryByPhone(user.getPhone());
        if (user1 != null) {
            Map<String, Object> map = new HashMap<>();
            map.put("error", -400);
            map.put("message", "手机号已存在");
            return map;
        }
        User regist = userService.regist(user);
        String name = file.getOriginalFilename();
        if (name.equals("")) {
            return regist;
        }
        user.setProfile(name);
        String realPath = request.getSession().getServletContext().getRealPath("img");
        try {
            file.transferTo(new File(realPath, name));
            Map<String, String> map = new HashMap<>();
            map.put("id", user.getId());
            map.put("profile", name);
            userService.updatePath(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
        userService.queryProvinceEcharts();
        userService.queryMothEcharts();
        return regist;
    }

    @RequestMapping("mothecharts")
    @ResponseBody
    public void mothecharts() {
        userService.queryMothEcharts();
    }

    @RequestMapping("provinceecharts")
    @ResponseBody
    public void provinceecharts() {
        userService.queryProvinceEcharts();
    }
}
