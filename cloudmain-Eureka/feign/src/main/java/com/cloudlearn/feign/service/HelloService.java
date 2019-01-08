package com.cloudlearn.feign.service;

import com.cloudlearn.feign.demo.Person;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

@Component
@FeignClient(name = "hello-service",fallback =HelloServiceHystrix.class)
public interface HelloService {

    @RequestMapping("hello")
    String hello();

    @RequestMapping("/feign-hello1")
    String feignHello1(@RequestParam("name") String name);
    @RequestMapping("/feign-hello2")
    Person feignHello2(@RequestHeader("name") String name,@RequestHeader("age") Integer age);
    @RequestMapping(value = "/feign-hello3",method = RequestMethod.POST)
    String feignHello3(@RequestBody Person person);
}
