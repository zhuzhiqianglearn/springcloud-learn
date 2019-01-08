package com.cloudlearn.zhuce.controller;

import com.cloudlearn.zhuce.demo.Person;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Random;


@RestController
public class HelloController {
    private final static org.apache.log4j.Logger log = org.apache.log4j.Logger.getLogger(HelloController.class);
    @Autowired
    private DiscoveryClient discoveryClient;

    @RequestMapping("/hello")
    public String Hello(){
        discoveryClient.getServices().forEach(id -> {
            discoveryClient.getInstances(id).forEach(instance -> {
                log.info("/hello, host:" + instance.getHost() + ", service_id:" + instance.getServiceId());
            });
        });
        return "Hello World";
    }
    @RequestMapping(value = "/hello-post",method = RequestMethod.POST)
    public String HelloPost(HttpServletRequest request,@RequestBody Person person){
        Random random=new Random(10);

        double i = Math.random()*10;
        System.out.println(i);
        if(i>4){
            int a=1/0;
        }
        return "Hello World----------------"+person.toString();
    }

    @RequestMapping("/yanshi")
    public String yanshi() throws InterruptedException {
        int i = new Random().nextInt(3000);
        System.out.println(i);
        Thread.sleep(i);
        return "Hello World";
    }
    //spring cloud Feign 专用
    @RequestMapping(value = "/feign-hello1",method = RequestMethod.GET)
    public String hello1(@RequestParam String name){
        this.cloudErro();
        return "Hello "+name;
    }
    @RequestMapping(value = "/feign-hello2",method = RequestMethod.GET)
    public Person hello2(@RequestHeader String name,@RequestHeader Integer age){
        Person p=new Person();
        p.setName(name);
        p.setAge(age);
        this.cloudErro();
        return  p;
    }
    @RequestMapping(value = "/feign-hello3",method = RequestMethod.POST)
    public String hello3(@RequestBody Person person){
        this.cloudErro();
        return person.toString();
    }


    public void cloudErro(){
        Random random=new Random(10);

        double i = Math.random()*10;
        System.out.println(i);
        if(i>4){
            int a=1/0;
        }
    }
}
