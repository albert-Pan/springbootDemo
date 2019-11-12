package com.pw.example.demo6;

import com.pw.example.demo6.entity.User;
import com.pw.example.demo6.mapper.UserMapper;
import com.pw.example.demo6.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo1ApplicationTests {

    @Autowired
    UserService userService;

    @Test
    public void contextLoads() {
        List<User> userList=userService.selectUsers();
        System.out.println(userList);
        User user=new User();
        user.setName("tom");
        user.setAge(18);
        userService.insertUser(user);
    }

}
