package com.yibiaopan.demo.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service("restfulSerivce")
public class RestfulServiceImpl {

    @Autowired
    private RestTemplate restTemplate ;

    /**
     * 定义服务降级逻辑(serviceFallback)
     * 并且自动的实现了线程调用得依赖隔离-实现线程池的隔离，
     * 为每一个依赖服务创建一个独立的线程池，这样就算某一个依赖服务出现延迟过高
     * 的情况，也只是对该依赖服务的调用产生影响，而不会拖慢其他的服务。
     *
     * Hystrix会将请求结果放入缓存，默认缓存key既是请求参数
     */
    @HystrixCommand(fallbackMethod = "serviceFallback")
    public String getRestData(){
        return restTemplate.getForEntity("http://hello-service/hello",String.class).getBody();
    }

    public String serviceFallback() {
        return "hi,sorry,error!";
    }
}
