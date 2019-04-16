package com.pw.example.demo3.controller;

import com.pw.example.demo3.model.PageResultVO;
import com.pw.example.demo3.model.TaskInfoVo;
import com.pw.example.demo3.service.JobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/job")
public class JobController {

    @Autowired
    JobService jobService;

    @RequestMapping(value ="/list" ,method = RequestMethod.GET)
    public PageResultVO<TaskInfoVo> list(@RequestParam("page") int page,@RequestParam("size") int size) {
        return  jobService.list(page,size);
    }
}
