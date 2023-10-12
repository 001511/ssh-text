package com.example.demo.controller;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("employee")
@Slf4j
public class EmployeeController {
    @RequestMapping("lists")
    public String lists(){
        log.debug("查询所有员工信息");
        return "emplist";
    }
}
