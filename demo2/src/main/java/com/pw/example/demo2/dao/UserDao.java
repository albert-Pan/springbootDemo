package com.pw.example.demo2.dao;

import com.pw.example.demo2.model.UserInfo;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

    @Select("select * from user")
    List<UserInfo> getAll();

}
