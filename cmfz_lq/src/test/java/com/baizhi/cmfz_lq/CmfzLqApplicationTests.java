package com.baizhi.cmfz_lq;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.baizhi.Annotation.UserAnnotation;
import com.baizhi.dao.AdminDAO;
import com.baizhi.entity.Admin;
import com.baizhi.entity.User;
import com.baizhi.service.CarouselService;
import com.baizhi.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.formula.functions.T;
import org.apache.poi.ss.usermodel.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class CmfzLqApplicationTests {
    @Autowired
    CarouselService carouselService;
    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        log.info("{}", new Date());
    }

    @Test
    public void test1() throws Exception {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("用户表");
        Row row = sheet.createRow(0);
        Cell cell = row.createCell(0);
        cell.setCellValue("编号");
        Cell cell1 = row.createCell(1);
        cell1.setCellValue(new Date());

        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        Font font = workbook.createFont();
        font.setColor(Font.COLOR_RED);
        cellStyle.setFont(font);

        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        cellStyle.setDataFormat(format);


        sheet.setColumnWidth(1, 5000);
        cell.setCellStyle(cellStyle);
        cell1.setCellStyle(cellStyle);
        workbook.write(new FileOutputStream("E:/a.xls"));
        workbook.close();
    }

    @Test

    //导出
    public void test2() throws Exception {
        List<User> users = userService.queryAll();
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("用户表");
        Row row = sheet.createRow(0);
//        String[] str={"编号","电话","日期"};
//        for(int i=0;i<str.length;i++){
//            Cell cell = row.createCell(i);
//            cell.setCellValue(str[i]);
//        }
        Class<User> userClass = User.class;
        Field[] declaredFields = userClass.getDeclaredFields();
        for (int i = 0; i < declaredFields.length; i++) {
            Field declaredField = declaredFields[i];
            UserAnnotation annotation = declaredField.getAnnotation(UserAnnotation.class);
            if (annotation != null) {
                String name = annotation.name();
                Cell cell = row.createCell(i);
                cell.setCellValue(name);
            }
        }

        CellStyle cellStyle = workbook.createCellStyle();
        DataFormat dataFormat = workbook.createDataFormat();
        short format = dataFormat.getFormat("yyyy-MM-dd");
        cellStyle.setDataFormat(format);

        for (int i = 0; i < users.size(); i++) {
            Row row1 = sheet.createRow(i + 1);
            Cell cell1 = row1.createCell(0);
            cell1.setCellValue(users.get(i).getId());
            Cell cell2 = row1.createCell(1);
            cell2.setCellValue(users.get(i).getPhone());
            Cell cell3 = row1.createCell(2);
            cell3.setCellValue(users.get(i).getRegistTime());
            cell3.setCellStyle(cellStyle);
        }
        sheet.setColumnWidth(2, 5000);
        workbook.write(new FileOutputStream("E:/b.xls"));
        workbook.close();
    }

    @Test
    //导入
    public void test3() throws Exception {
        Workbook workbook = new HSSFWorkbook(new FileInputStream("E:/b.xls"));
        Sheet sheetAt = workbook.getSheetAt(0);
        int lastRowNum = sheetAt.getLastRowNum();
        for (int i = 0; i < lastRowNum; i++) {
            Row row = sheetAt.getRow(i + 1);
            Cell cell = row.getCell(0);
            System.out.print(cell.getStringCellValue());
            Cell cell1 = row.getCell(1);
            System.out.print(cell1.getStringCellValue());
            Cell cell2 = row.getCell(2);
            System.out.print(cell2.getDateCellValue());
            System.out.println();
        }
    }

    @Test
    public void test4() throws Exception {
        List<User> users = userService.queryAll();
        Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams("用户", "学生"),
                User.class, users);
        workbook.write(new FileOutputStream("E:/c.xls"));
        workbook.close();
    }

    @Test
    public void test5() {
        userService.queryMothEcharts();
    }

    @Test
    public void test6() {
        userService.queryMothEcharts();
        System.out.println("111111111111111111");
    }

    @Test
    public void test7() {
        userService.queryProvinceEcharts();
    }

    @Test
    public void test8() {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAIayx5CNbMf8Cc", "Kvn0qmnYGejbOK8sNWT3eQQe9Oea8D");
        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", "13273770601");
        request.putQueryParameter("SignName", "娇十七");
        request.putQueryParameter("TemplateCode", "SMS_171116725");
        request.putQueryParameter("TemplateParam", "{\"code\":\"tttt\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void test9() {
        String name = stringRedisTemplate.opsForValue().get("name");
        System.out.println(name);
    }

    @Test
    public void testShiro() {
        IniSecurityManagerFactory iniSecurityManagerFactory = new IniSecurityManagerFactory();
        SecurityManager instance = iniSecurityManagerFactory.getInstance();
        SecurityUtils.setSecurityManager(instance);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("liuqi", "123456");
        try {
            subject.login(token);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean authenticated = subject.isAuthenticated();
        boolean vip = subject.hasRole("vip");
        System.out.println(authenticated);
        if(vip){
            System.out.println("可以使用vip所有权限");
        }else {
            System.out.println("不是vip");
        }
        boolean permitted = subject.isPermitted("article:delete");
        if(permitted){
            System.out.println("可以对文章进行删除");
        }else {
            System.out.println("没有权限删除文章");
        }
    }
    @Autowired
    AdminDAO adminDAO;
    @Test
    public void test10(){
        Admin admin = adminDAO.selectAdmin("liuqi");
        System.out.println(admin);
    }
}
