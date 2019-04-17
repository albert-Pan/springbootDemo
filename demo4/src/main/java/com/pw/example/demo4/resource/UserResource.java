package com.pw.example.demo4.resource;

import com.pw.example.demo4.dao.UserDao;
import com.pw.example.demo4.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserResource {
    @Autowired
    UserDao userDao;

    @Cacheable(value = "d",unless = "#result == null", key = "'users'")
    public List<UserInfo> getAllUser(){
        return  userDao.getAll();
    }
}
