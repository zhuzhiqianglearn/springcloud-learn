package com.cloudlearn.xiaofeizhe.service;

import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

public class Person2Command extends HystrixCommand<String> {
    private RestTemplate restTemplate;
    private Long id;
    public Person2Command(Setter setter, RestTemplate restTemplate, Long id) {
        super(setter);
        this.restTemplate=restTemplate;
        this.id=id;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://hello-service/hello",String.class);
    }

    @Override
    protected String getFallback() {
        return "HystrixCommand<String> 返回失败";
    }
}
