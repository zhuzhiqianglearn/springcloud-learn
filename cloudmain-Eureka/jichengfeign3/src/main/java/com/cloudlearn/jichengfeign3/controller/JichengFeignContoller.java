package com.cloudlearn.jichengfeign3.controller;

import com.cloudlearn.hellojicheng.controller.UserController;
import com.cloudlearn.hellojicheng.mode.User;
import com.cloudlearn.jichengfeign3.service.FeignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JichengFeignContoller implements UserController{
    @Autowired
    private FeignService feignService;
    @Override
    public String feignJichengHello1(String name) {
        return feignService.feignJichengHello1(name);
    }

    @Override
    public User feignJichengHello2(String name, String age) {
        return feignService.feignJichengHello2(name,age);
    }

    @Override
    public String feignJichengHello3(@RequestBody User user) {
        return feignService.feignJichengHello3(user);
    }
    @RequestMapping("/abcde")
    public String feignJichengHello4(User user) {
        return feignService.feignJichengHello3(user);
    }
}
