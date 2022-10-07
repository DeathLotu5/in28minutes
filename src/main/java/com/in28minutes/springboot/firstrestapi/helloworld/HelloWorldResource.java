package com.in28minutes.springboot.firstrestapi.helloworld;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

//@Controller
@RestController
public class HelloWorldResource {

    @RequestMapping("/hello-word")
//    @ResponseBody
    public String helloWorld() {
        return "hello world";
    }

    @RequestMapping("/hello-world-bean")
    public HelloWorld helloWorldBean() {
        return new HelloWorld("hello world");
    }

    @RequestMapping("/hello-world-bean-path-param/{name}")
    public HelloWorld helloWorldBeanPathParam(@PathVariable String name) {
        return new HelloWorld("hello world, " + name);
    }

    @RequestMapping("/hello-world-bean-multiple-path-param/{name}/message/{message}")
    public HelloWorld helloWorldBeanMultiplePathParam(@PathVariable String name, @PathVariable String message) {
        return new HelloWorld("hello world, " + name + ", " + message);
    }

}
