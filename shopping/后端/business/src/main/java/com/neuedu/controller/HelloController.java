package com.neuedu.controller;

import com.neuedu.config.AppConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class HelloController {
   @Autowired
   AppConfig appConfig;
    @RequestMapping(value = {"/hello","/hello1"})
    public String hello( @RequestParam(value="useername" ,required = false,defaultValue = "admin") String username){
        return username;
    }

    @RequestMapping("/login/{name}/{pwd}")
    public String test(@PathVariable("name") String name, @PathVariable("pwd") String pwd){
        return name+"  "+pwd;
    }
}


