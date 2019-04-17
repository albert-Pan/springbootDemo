package com.pw.example.demo4.dao;

import com.pw.example.demo4.model.RoleInfo;
import com.pw.example.demo4.model.UserInfo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface UserDao {

    @Select("select * from sys_user_info where username=#{username}")
    UserInfo findUser(@Param("username") String username);

    /**
     * 获取用户对应的角色
     * @param userInfo
     * @return
     */
    @Select("select sys_role.role from (select uid,rid from sys_user_role where uid=#{uid}) sur  " +
            "JOIN sys_role on sur.rid=sys_role.rid ")
    List<RoleInfo> getUserRoleList(UserInfo userInfo) ;

    @Select("select * from sys_user_info")
    List<UserInfo> getAll();


}
