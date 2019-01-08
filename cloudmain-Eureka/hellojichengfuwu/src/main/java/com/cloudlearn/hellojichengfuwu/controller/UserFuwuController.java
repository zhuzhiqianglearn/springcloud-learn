package com.cloudlearn.hellojichengfuwu.controller;

import com.cloudlearn.hellojicheng.controller.UserController;
import com.cloudlearn.hellojicheng.mode.User;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserFuwuController implements UserController {
    @Override
    public String feignJichengHello1(String name) {
        return "Hello-JiCheng "+name;
    }

    @Override
    public User feignJichengHello2(String name, String age) {
        User user=new User(name,age);
        return user;
    }

    @Override
    public String feignJichengHello3(@RequestBody User user) {
        return user.toString();
    }
}
