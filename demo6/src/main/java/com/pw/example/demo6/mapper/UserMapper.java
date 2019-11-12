package com.pw.example.demo6.mapper;

import com.pw.example.demo6.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @Classname UserMapper
 * @Description TODO
 * @Date 2019/11/12 0012 下午 3:11
 * @Created by albert
 */
public interface UserMapper {

    @Insert("INSERT INTO `user` ( `name`, `age`) VALUES (#{name},#{age})")
    int insertUser(User user);

    @Select("select * from user")
    List<User> selectUsers();
}
