package com.pw.example.demo4.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

//@Setter
//@Getter
public class BaseInfo implements Serializable {
    private String token;
    private List<String> roles;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }
}
