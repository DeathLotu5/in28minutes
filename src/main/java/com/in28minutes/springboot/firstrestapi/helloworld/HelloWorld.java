package com.in28minutes.springboot.firstrestapi.helloworld;

public class HelloWorld {

    public HelloWorld(String message) {
        this.message = message;
    }
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HelloWorld{" +
                "message='" + message + '\'' +
                '}';
    }
}
