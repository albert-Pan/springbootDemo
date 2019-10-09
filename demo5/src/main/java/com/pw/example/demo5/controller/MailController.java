package com.pw.example.demo5.controller;

import com.pw.example.demo5.entity.MailInfo;
import com.pw.example.demo5.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Classname MailController
 * @Description TODO
 * @Date 2019/10/9 0009 下午 5:36
 * @Created by albert
 */
@RestController
@RequestMapping(value = "/mail")
public class MailController {

    @Autowired
    MailService mailService;

    @RequestMapping(value ="/sendTemplateHtmlMail" ,method = RequestMethod.GET)
    public void sendMail(MailInfo mailInfo){
        mailService.sendTemplateHtmlMail(mailInfo);
    }

    @RequestMapping(value ="/sendSimpleMail" ,method = RequestMethod.GET)
    public void sendMail(String to) throws Exception {
        mailService.sendSimpleMail(to,"test","test");
    }

}
