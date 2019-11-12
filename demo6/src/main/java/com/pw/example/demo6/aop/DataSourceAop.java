package com.pw.example.demo6.aop;

import com.pw.example.demo6.config.DBContextHolder;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @Classname DataSourceAop
 * @Description TODO
 * @Date 2019/10/12 0012 下午 5:09
 * @Created by albert
 */
@Aspect
@Component
public class DataSourceAop {

    @Pointcut("!@annotation(com.pw.example.demo6.entity.annotation.Master) " +
            "&& (execution(* com.pw.example.demo6.service..*.select*(..)) " +
            "|| execution(* com.pw.example.demo6.service..*.get*(..)))")
    public void readPointcut() {

    }

    //包含Master的注解,或者方法名满足下面的条件的走master库
    @Pointcut("@annotation(com.pw.example.demo6.entity.annotation.Master) " +
            "|| execution(* com.pw.example.demo6.service..*.insert*(..)) " +
            "|| execution(* com.pw.example.demo6.service..*.add*(..)) " +
            "|| execution(* com.pw.example.demo6.service..*.update*(..)) " +
            "|| execution(* com.pw.example.demo6.service..*.edit*(..)) " +
            "|| execution(* com.pw.example.demo6.service..*.delete*(..)) " +
            "|| execution(* com.pw.example.demo6.service..*.remove*(..))")
    public void writePointcut() {

    }

    @Before("readPointcut()")
    public void read() {
        DBContextHolder.slave();
    }

    @Before("writePointcut()")
    public void write() {
        DBContextHolder.master();
    }
}
