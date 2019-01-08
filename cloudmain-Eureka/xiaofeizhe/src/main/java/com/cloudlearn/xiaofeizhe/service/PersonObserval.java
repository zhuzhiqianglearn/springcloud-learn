package com.cloudlearn.xiaofeizhe.service;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixObservableCommand;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;

public class PersonObserval extends HystrixObservableCommand<String>{

    private RestTemplate restTemplate;
    private Long id;

    public PersonObserval(Setter setter, RestTemplate restTemplate, Long id) {
        super(setter);
        this.restTemplate=restTemplate;
        this.id=id;
    }


    @Override
    protected Observable<String> construct() {
        return Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try{
                    if(!subscriber.isUnsubscribed()){
                       String s= restTemplate.getForObject("http://hello-service/yanshi",String.class);
                       subscriber.onNext(s);
                       subscriber.onCompleted();
                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    protected Observable<String> resumeWithFallback() {
        return Observable.unsafeCreate(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                String s="cuowu--------------";
                subscriber.onNext(s);
                subscriber.onCompleted();
            }
        });
    }
}
