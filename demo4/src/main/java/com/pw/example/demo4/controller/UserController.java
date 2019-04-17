package com.pw.example.demo4.controller;

import com.pw.example.demo4.dao.UserDao;
import com.pw.example.demo4.model.Response;
import com.pw.example.demo4.model.ResponseCode;
import com.pw.example.demo4.model.UserInfo;
import com.pw.example.demo4.resource.UserResource;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserResource resource;


    @RequestMapping(value ="/list" ,method = RequestMethod.GET)
    public Response getAllUser(){
        return  Response.buildResponse(ResponseCode.SUCCESS,resource.getAllUser());
    }

    /**
     * 未登录，shiro应重定向到登录界面，此处返回未登录状态信息由前端控制跳转页面
     * @return
     */
    @RequestMapping(value = "/unauth")
    public Response unauth() {
        return  Response.buildResponse(ResponseCode.UNAUTH,false);
    }





    @RequiresRoles(value={"admin"},logical= Logical.OR)
    @RequestMapping(value="/authtest",method= RequestMethod.POST)
    public Response authtest(){
        return Response.buildResponse(ResponseCode.SUCCESS,"authtest");
    }

    @RequestMapping(value = "/notRole")
    public Response notRole() {
        return   Response.buildResponse(ResponseCode.UNAUTHORIZED,false);
    }



}
