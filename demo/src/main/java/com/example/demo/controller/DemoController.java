package com.example.demo.controller;

import com.example.demo.bean.Student;
import com.example.demo.serialize.SerializeUtil;
import com.example.demo.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sql.DataSource;
import java.io.File;
import java.sql.SQLException;

@Controller
public class DemoController {

    @Autowired
    ApplicationArguments applicationArguments;
    @Autowired
    TestService testService;
    @Autowired
    DataSource dataSource;

    @RequestMapping("/demo")
    @ResponseBody
    public String demo() throws Exception {
        System.out.println(dataSource.getConnection());
        System.out.println(dataSource);
        return "demo";
    }

    @RequestMapping("/test")
    @ResponseBody
    public String test(){
        return testService.test();
    }

    @RequestMapping("/serialize")
    @ResponseBody
    public String serialize(){
        Student student = new Student();
        student.setName("Lenard");
        student.setAge(29);
        student.setScore(45);
        SerializeUtil.serialize(student);
        return "success";
    }
    @RequestMapping("/deserialize")
    @ResponseBody
    public String deserialize(){
        File file = new File("student.txt");
        Student student = SerializeUtil.deserialize(file);
        return "success";
    }
}
