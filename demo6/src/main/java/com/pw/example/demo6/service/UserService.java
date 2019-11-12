package com.pw.example.demo6.service;

import com.pw.example.demo6.entity.User;
import com.pw.example.demo6.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname UserService
 * @Description TODO
 * @Date 2019/11/12 0012 下午 3:18
 * @Created by albert
 */
@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

   public int insertUser(User user){
       return userMapper.insertUser(user);
   }

    public List<User> selectUsers() {
        return userMapper.selectUsers();
    }

}
