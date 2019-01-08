package com.cloudlearn.xiaofeizhe.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixRequestCache;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.sun.javafx.image.impl.ByteIndexed;
import org.springframework.web.client.RestTemplate;

public class PersonHuancunCommand extends HystrixCommand<String> {
    private RestTemplate restTemplate;
    private Long id;
    public PersonHuancunCommand(Setter setter, RestTemplate restTemplate, Long id) {
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
        return "HystrixCommand<String> 返回失败--缓存";
    }

    @Override
    protected String getCacheKey() {
        return String.valueOf(id);
    }
    public static void flushRequestCache(Long id){
        HystrixRequestCache huancunComman = HystrixRequestCache.getInstance(
                HystrixCommandKey.Factory.asKey("huancunComman"), HystrixConcurrencyStrategyDefault.getInstance());
             huancunComman.clear(String.valueOf(1L));
    }
}
