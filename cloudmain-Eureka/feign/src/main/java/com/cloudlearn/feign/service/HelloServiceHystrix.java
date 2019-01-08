package com.cloudlearn.feign.service;

import com.cloudlearn.feign.demo.Person;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class HelloServiceHystrix implements HelloService{
    @Override
    public String hello() {
        return "hello---erro";
    }

    @Override
    public String feignHello1(@RequestParam("name")String name) {
        return "hello1---erro";
    }

    @Override
    public Person feignHello2(String name, Integer age) {
        Person person = new Person();
        person.setName("未知");
        return person;
    }

    @Override
    public String feignHello3(Person person) {
        return "hello3---erro";
    }
}
