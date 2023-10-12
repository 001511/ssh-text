package com.example.demo.controller;

import com.example.demo.entity.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.VerifyCodeUtils;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@Controller
@RequestMapping("User")
@Slf4j
public class UserController {

    private final UserService userService;
    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("login")
    public String login(String user_name,String password, HttpSession session){
        log.debug("本地登录姓名：{}", user_name);
        log.debug("本地登录密码：{}", password);
        try {
            User user = userService.login(user_name, password);
            session.setAttribute("user", user);
        }catch (Exception e) {
            e.printStackTrace();
            return "redirect:/login";
        }
        return "redirect:/employee/lists";

    }
    @RequestMapping("register")
    public String register(User user, String code, HttpSession session){
        log.debug("用户：{}，密码：{}", user.getUser_name(), user.getPassword());
        log.debug("用户输入验证码：{}", code);

        try {
            String sessionCode = session.getAttribute("code").toString();

        if (!sessionCode.equalsIgnoreCase(code))throw new RuntimeException("验证码错误！");

        userService.register(user);
        } catch (RuntimeException e){
            e.printStackTrace();
            return "redirect:/register";
        }
        return "redirect:/login";
    }
    @RequestMapping("generateImageCode")
    public void generateImageCode(HttpSession session, HttpServletResponse response) throws IOException {
        String code = VerifyCodeUtils.generateVerifyCode(4);
        session.setAttribute("code",code);
        response.setContentType("image/png");
        ServletOutputStream os = response.getOutputStream();
        VerifyCodeUtils.outputImage(220, 60, os, code);
    }
}
