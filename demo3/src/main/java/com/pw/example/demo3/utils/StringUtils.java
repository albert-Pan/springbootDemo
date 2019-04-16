package com.pw.example.demo3.utils;

public class StringUtils {
    public static boolean isAnyBlank(String... args){
        for (String arg : args) {
           if(org.springframework.util.StringUtils .isEmpty(arg)){
               return true;
           }
        }
        return false;
    }

    public static boolean isEmpty(String arg){
        return  org.springframework.util.StringUtils .isEmpty(arg);
    }
}
