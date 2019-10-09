package com.pw.example.demo5.service;

import com.pw.example.demo5.entity.MailInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.Map;

/**
 * 用于发送邮件的辅助类
 */
@Service
@Slf4j
public class MailService{
    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String mailFrom;

    @Autowired
    private TemplateEngine templateEngine;


    /**
     * 发送html类型的邮件
     * @param to
     * @param subject
     * @param content
     */
    public void sendHtmlMail(String[] to, String subject, String content) {
        MimeMessage message = mailSender.createMimeMessage();
        if(content==null){
            log.info("[html邮件内容为空] to-{},subject-{}");
            return;
        }
        try {
            //true表示需要创建一个multipart message
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setFrom(mailFrom);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            long startTime=System.currentTimeMillis();
            mailSender.send(message);
            log.info("[html邮件发送成功] to-{},subject-{} cost time={}",to,subject,(System.currentTimeMillis()-startTime));
        } catch (Exception e) {
            log.error("发送html邮件时发生异常！", e);
        }
    }

    /**
     * 发送简单的邮件
     * @param to
     * @param subject
     * @param text
     * @throws Exception
     */
    public void sendSimpleMail(String to,String subject,String text) throws Exception {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(mailFrom);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);

    }

    /**
     * 发送模版类邮件
     * @param templateName
     * @param tos
     * @param subject
     * @param valueMap
     */
    public void sendTemplateHtmlMail(String templateName,String[] tos, String subject,Map<String, Object> valueMap) {
        // 添加正文（使用thymeleaf模板）
        Context context = new Context();
        context.setVariables(valueMap);
        //获取模板html代码
        String process = templateEngine.process(templateName, context);
        sendHtmlMail(tos,subject,process);
    }


    public void sendTemplateHtmlMail(MailInfo mailInfo) {
        // 添加正文（使用thymeleaf模板）
        Context context = new Context();
        context.setVariables(mailInfo.getValueMap());
        //获取模板html代码
        String process = templateEngine.process(mailInfo.getTemplateName(), context);
        sendHtmlMail(mailInfo.getTos(),mailInfo.getSubject(),process);
    }

}
