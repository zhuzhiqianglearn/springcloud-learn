package com.cloudlearn.xiaofeizhe.service;

import com.cloudlearn.xiaofeizhe.demo.Person;
import com.netflix.hystrix.HystrixObservableCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.ObservableExecutionMode;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheKey;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheRemove;
import com.netflix.hystrix.contrib.javanica.cache.annotation.CacheResult;
import com.netflix.hystrix.contrib.javanica.command.AsyncResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Subscriber;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.concurrent.Future;

@Service
public class HelloService {
    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "erro")
    public String succese(){
        Person person=new Person();
        person.setAddress("beijing");
        person.setAge(18);
        person.setName("zzq");
        return restTemplate.postForObject("http://hello-service/hello-post",person,String.class);
    }
    @HystrixCommand(fallbackMethod = "erro")
    public String yanshi(){
        Person person=new Person();
        person.setAddress("beijing");
        person.setAge(18);
        person.setName("zzq");
        return restTemplate.postForObject("http://hello-service/yanshi",person,String.class);
    }
    @HystrixCommand(fallbackMethod = "erro")
    public Future<String> succeseYibu(){
        return  new AsyncResult<String>() {
            @Override
            public String invoke() {
                Person person=new Person();
                person.setAddress("beijing");
                person.setAge(18);
                person.setName("zzq");
                return  restTemplate.postForObject("http://hello-service/yanshi",person,String.class);
            }
        };
    }
@HystrixCommand(fallbackMethod = "erroYibu",observableExecutionMode = ObservableExecutionMode.EAGER)
public Observable<String> HystrixCommandTongbu(){
        return Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s= restTemplate.getForObject("http://hello-service/yanshi",String.class);
                subscriber.onNext(s);
                subscriber.onCompleted();
            }
        });
}

    @HystrixCommand(observableExecutionMode = ObservableExecutionMode.LAZY)
    public Observable<String> HystrixCommandYibu(){
        return Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s= restTemplate.getForObject("http://hello-service/yanshi",String.class);
                subscriber.onNext(s);
                subscriber.onCompleted();
            }
        });
    }

    public Observable<String> erroYibu(){
        return Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s= "HystrixCommand----Observable观察模式错误---------";
                subscriber.onNext(s);
                subscriber.onCompleted();
            }
        });
    }

    @HystrixCommand(fallbackMethod = "erro",commandKey = "mingming",groupKey = "mingmingGroup",threadPoolKey = "getMingming")
    public String mingming(){
        Person person=new Person();
        person.setAddress("beijing");
        person.setAge(18);
        person.setName("zzq");
        return restTemplate.postForObject("http://hello-service/hello-post",person,String.class);
    }
    public String erro(Throwable throwable){
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        throwable.printStackTrace(pw);

        return "404-----------405----------"+sw.toString();
    }
//    @CacheResult(cacheKeyMethod = "getCacheKey")
    @HystrixCommand(commandKey = "commandKey1")
    public String openCacheByAnnotation1(@CacheKey Long id){
        //此次结果会被缓存
        return restTemplate.getForObject("http://hello-service/hello", String.class);
    }

    /**
     * 使用注解清除缓存 方式1
     * @CacheRemove 必须指定commandKey才能进行清除指定缓存
     */
    @CacheRemove(commandKey = "commandKey1", cacheKeyMethod = "getCacheKey")
    @HystrixCommand
    public void flushCacheByAnnotation1(Long id){
        System.out.println("缓存清空");
        //这个@CacheRemove注解直接用在更新方法上效果更好
    }

    /**
     * 第一种方法没有使用@CacheKey注解，而是使用这个方法进行生成cacheKey的替换办法
     * 这里有两点要特别注意：
     * 1、这个方法的入参的类型必须与缓存方法的入参类型相同，如果不同被调用会报这个方法找不到的异常
     * 2、这个方法的返回值一定是String类型
     */
    public String getCacheKey(Long id){
        return String.valueOf(id);
    }
}
