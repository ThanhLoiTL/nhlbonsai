package com.nhlshop.dto;

public class ResponseLogin {
    private String status;
    private String message;
    private Object data;
    private String jwt;

    public ResponseLogin(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseLogin(String status, String message, Object data, String jwt) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }

    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public ResponseLogin() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
