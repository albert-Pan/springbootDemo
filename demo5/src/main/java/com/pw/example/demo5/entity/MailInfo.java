package com.pw.example.demo5.entity;

import lombok.Data;

import java.util.Map;

@Data
public class MailInfo {
    private String templateName;
    //private String to;
    private String[] tos;
    private String subject;
    private Map<String, Object> valueMap;
}