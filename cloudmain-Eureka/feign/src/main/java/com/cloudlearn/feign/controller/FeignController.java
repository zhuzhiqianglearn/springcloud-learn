package com.cloudlearn.feign.controller;

import com.cloudlearn.feign.demo.Person;
import com.cloudlearn.feign.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FeignController {
    @Autowired
    private HelloService helloService;
    @RequestMapping(value = "/feign-first",method = RequestMethod.GET)
    public String first()
    {
        return  helloService.hello();
    }
    @RequestMapping("/feign-first1")
    public String first1()
    {
        return  "sdsfdsfdas";
    }

    @RequestMapping("/feign-hello1")
    public String feignHello1(String name)
    {
        return  helloService.feignHello1(name);
    }
    @RequestMapping("/feign-hello2")
    public String feignHello2(String name,Integer age)
    {
        return  helloService.feignHello2(name,age).toString();
    }
    @RequestMapping("/feign-hello3")
    public String feignHello3(Person person)
    {
        return  helloService.feignHello3(person);
    }
}
