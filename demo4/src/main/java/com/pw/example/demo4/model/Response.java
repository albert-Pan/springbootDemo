package com.pw.example.demo4.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Response<T> {
    private int code;
    private T data;
    private String message;

    public static Response buildResponse(ResponseCode responseCode,Object data){
        Response response = new Response();
        response.setCode(responseCode.getCode());
        response.setMessage(responseCode.getMessage());
        response.setData(data);
        return  response;
    }

}
