package com.pw.example.demo3.model;

import lombok.Data;

import java.util.Date;

@Data
public class TaskInfoVo {

    private String jobName;
    private String jobGroup;
    private String jobDescription;
    private String jobStatus;
    private String cronExpression;
    private String createTime;

    private Date previousFireTime;
    private Date nextFireTime;

}
