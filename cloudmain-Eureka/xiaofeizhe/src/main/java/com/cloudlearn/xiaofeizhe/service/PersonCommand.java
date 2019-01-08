package com.cloudlearn.xiaofeizhe.service;

import com.cloudlearn.xiaofeizhe.demo.Person;
import com.netflix.hystrix.HystrixCommand;
import org.springframework.web.client.RestTemplate;

public class PersonCommand extends HystrixCommand<String> {
    private RestTemplate restTemplate;
    private Long id;
    public PersonCommand(Setter setter, RestTemplate restTemplate,Long id) {
        super(setter);
        this.restTemplate=restTemplate;
        this.id=id;
    }

    @Override
    protected String run() throws Exception {
        return restTemplate.getForObject("http://hello-service/yanshi",String.class);
    }

    @Override
    protected String getFallback() {
        return "HystrixCommand<String> 返回失败";
    }
}
