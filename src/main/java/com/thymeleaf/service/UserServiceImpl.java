package com.thymeleaf.service;

import com.thymeleaf.dao.UserDao;
import com.thymeleaf.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.nio.charset.StandardCharsets;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void register(User user) {
        //1.根据用户名查询数据库中是否存在改用户
        User userDB = userDao.findByUserName(user.getUser_name());
        //2.判断用户是否存在
        if(!ObjectUtils.isEmpty(userDB)) throw new RuntimeException("当前用户名已被注册!");
        //3.注册用户&明文的密码加密  特点: 相同字符串多次使用md5就行加密 加密结果始终是一致
        userDao.save(user);


    }

    @Override
    public User login(String user_name, String password) {
        User user=userDao.findByUserName(user_name);
        if (ObjectUtils.isEmpty(user))throw new RuntimeException("用户名不正确！");
        if (!user.getPassword().equals(password))throw new RuntimeException("密码输入错误！");
        return user;
    }
}
