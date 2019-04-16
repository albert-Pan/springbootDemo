package com.pw.example.demo3;

import com.pw.example.demo3.model.UserInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Demo3ApplicationTests {

    @Test
    public void contextLoads() {
        UserInfo userInfo=new UserInfo();
    }

}
