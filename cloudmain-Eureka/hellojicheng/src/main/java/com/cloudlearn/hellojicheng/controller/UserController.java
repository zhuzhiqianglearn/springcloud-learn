package com.cloudlearn.hellojicheng.controller;

import com.cloudlearn.hellojicheng.mode.User;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

public interface UserController {
    @RequestMapping(value = "feign-jicheng-hello1",method = RequestMethod.GET)
    String feignJichengHello1(@RequestParam("name") String name);
    @RequestMapping(value = "feign-jicheng-hello2",method = RequestMethod.GET)
    User feignJichengHello2(@RequestHeader("name") String name, @RequestHeader("age") String age);
    @RequestMapping(value = "feign-jicheng-hello3",method = RequestMethod.POST)
    String feignJichengHello3(@RequestBody User user);
}
