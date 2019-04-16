package com.pw.example.demo3.dao;

import com.pw.example.demo3.model.UserInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

    @Select("select * from user")
    List<UserInfo> getAll();

}
