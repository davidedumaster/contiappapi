package com.py.contiappapi;

public class HttpResponse {
    private int statusCode;
    private String data;

    public HttpResponse(int statusCode, String content) {
        this.statusCode = statusCode;
        this.data = content;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getContent() {
        return data;
    }
}
