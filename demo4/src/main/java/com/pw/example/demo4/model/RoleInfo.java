package com.pw.example.demo4.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RoleInfo {
    private long id;
    private String role;
    private String description;
    private boolean available;
}
