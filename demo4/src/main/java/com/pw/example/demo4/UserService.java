package com.pw.example.demo4;
import com.pw.example.demo4.dao.UserDao;
import com.pw.example.demo4.model.RoleInfo;
import com.pw.example.demo4.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Administrator on 2019/3/19 0019.
 */
@Service
public class UserService {

    @Autowired
    UserDao userDao;

    @Cacheable(value = "user", unless = "#result == null", key = "'u:'+#username")
    public UserInfo findUser(String username){
        UserInfo userInfo=  userDao.findUser(username);
        return userInfo;
    }

    public List<RoleInfo> getUserRoleList(UserInfo userInfo){
        return  userDao.getUserRoleList(userInfo);
    }


}

