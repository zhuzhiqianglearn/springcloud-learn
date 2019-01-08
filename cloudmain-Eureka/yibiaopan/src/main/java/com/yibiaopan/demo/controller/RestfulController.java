package com.yibiaopan.demo.controller;

import com.yibiaopan.demo.service.RestfulServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rest")
@PropertySource("classpath:application.properties")
public class RestfulController {

    @Autowired
    private RestfulServiceImpl restfulService ;

    @Value("${server.port}")
    String port ;
    @Value("${spring.application.name}")
    String name ;

    @RequestMapping("/home")
    public String home(){
        return "I am info from port("+port+") from application name("+name+") invoke other service." + "\n"
                +restfulService.getRestData();
    }
}
