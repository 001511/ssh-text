package com.example.demo.service;

import com.example.demo.dao.UserDao;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@Transactional
public class UserServiceImpl implements UserService{
    private UserDao userDao;
    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void register(User user) {
        User userDB = userDao.findByUserName(user.getUser_name());
        if(!ObjectUtils.isEmpty(userDB)) throw new RuntimeException("当前用户名已被注册！");
        userDao.save(user);
    }

    @Override
    public User login(String user_name, String password) {
        User user = userDao.findByUserName(user_name);
        if (ObjectUtils.isEmpty(user))throw new RuntimeException("用户名不正确");
        if (!user.getPassword().equals(password))throw new RuntimeException("密码输入错误！");
        return user;
    }
}
