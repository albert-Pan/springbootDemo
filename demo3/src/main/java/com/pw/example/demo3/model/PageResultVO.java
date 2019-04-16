package com.pw.example.demo3.model;

import lombok.Data;

import java.util.List;

@Data
public class PageResultVO<T> {
    private long total;
    private List<T>  rows;

}
