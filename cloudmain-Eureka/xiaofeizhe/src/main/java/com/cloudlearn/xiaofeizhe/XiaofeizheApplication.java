package com.cloudlearn.xiaofeizhe;

import com.cloudlearn.xiaofeizhe.demo.Person;
import com.cloudlearn.xiaofeizhe.service.*;
import com.netflix.hystrix.*;
import com.netflix.hystrix.contrib.javanica.aop.aspectj.HystrixCommandAspect;
import com.netflix.hystrix.contrib.metrics.eventstream.HystrixMetricsStreamServlet;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import rx.Observable;
import rx.Observer;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

//注解开启断路器功能
@EnableCircuitBreaker
//开启缓存全局拦截
@ServletComponentScan
@EnableDiscoveryClient
@SpringBootApplication
@RestController
@EnableHystrixDashboard
public class XiaofeizheApplication {
    @Bean
    public ServletRegistrationBean getServlet(){

        HystrixMetricsStreamServlet streamServlet = new HystrixMetricsStreamServlet();

        ServletRegistrationBean registrationBean = new ServletRegistrationBean(streamServlet);

        registrationBean.setLoadOnStartup(1);

        registrationBean.addUrlMappings("/actuator/hystrix.stream");

        registrationBean.setName("HystrixMetricsStreamServlet");


        return registrationBean;
    }

	@Bean
    //负载均衡注解。默认是轮询访问
	@LoadBalanced
	RestTemplate restTemplate(){
		return new RestTemplate();
	}
	//使@HystrixCommand注解实现异步调用，方法的实现使用AsyncResult，方法请查看 helloService的succeseYibu（）方法

	@Bean
    public HystrixCommandAspect hystrixCommandAspect() {
        return new HystrixCommandAspect();
    }

	@Autowired
	private  HelloService helloService;

	public static void main(String[] args) {
		SpringApplication.run(XiaofeizheApplication.class, args);
	}

	//springcloud Ribbon负载转发--------------------------------------------------------------
	@RequestMapping("/xiaofeizhe")
	public String xiaofeizhe(){
        System.out.println(111111111);
        String forObject = restTemplate().getForObject("http://hello-service/yanshi", String.class);
        System.out.println(forObject);
        System.out.println(222222222);
		return restTemplate().getForEntity("http://hello-service/hello",String.class).getBody();
	}
	@RequestMapping("/xiaofeizhe1")
	public String xiaofeizhe1(){
		return restTemplate().getForObject("http://hello-service/hello",String.class);
	}
	@RequestMapping("/xiaofeizhe-post")
	public String xiaofeizhePost(){
		Person person=new Person();
		person.setAddress("beijing");
		person.setAge(18);
		person.setName("zzq");
		return restTemplate().postForObject("http://hello-service/hello-post",person,String.class);
	}
	@RequestMapping("/xiaofeizhe-post1")
	public String xiaofeizhePost1(){
		Person person=new Person();
		person.setAddress("beijing");
		person.setAge(18);
		person.setName("zzq");
		return restTemplate().postForEntity("http://hello-service/hello-post",person,String.class).getBody();
	}



   //springcloud断路器-----------------------------------------------------------------
	@RequestMapping("/xiaofeizhe-duanlu")
	public String duanlu(){

		return helloService.succese();
	}
	@RequestMapping("/xiaofeizhe-duanlu-yanshi")
	public String yanshi(){

		return helloService.yanshi();
	}

	//继承HystrixCommand同步执行
	@RequestMapping("/xiaofeizhe-agin")
	public String agin(){
        PersonCommand myfirstHy = new PersonCommand(HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("myfirstHy")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(2000)), restTemplate(), 1l);
        return myfirstHy.execute();
	}
    //继承HystrixCommand异步执行
    @RequestMapping("/xiaofeizhe-agin2")
    public String agin2(){
        PersonCommand myfirstHy = new PersonCommand(HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("myfirstHy")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(2000)), restTemplate(), 1l);
        Future<String> queue = myfirstHy.queue();
        String s="";
        String s2="";
        Person2Command myfirstHy2 = new Person2Command(HystrixCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("myfirstHy")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(2000)), restTemplate(), 1l);
        Future<String> queue1 = myfirstHy2.queue();
        System.out.println("------------");
        try {
            s2=queue1.get();
             s = queue.get();
            System.out.println(s2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return s2+"------------"+s;
    }
    //HystrixCommand注解异步调用
    @RequestMapping("/xiaofeizhe-duanlu-HystrixCommandYibu")
    public String HystrixCommandYibu() throws ExecutionException, InterruptedException {
        Future<String> stringFuture = helloService.succeseYibu();
        System.out.println("HystrixCommandYibu---------异步调用方法---------");
        String s = stringFuture.get();
        System.out.println(s);
        return s;
    }

    @RequestMapping("/xiaofeizhe-agin2-observable")
    public String observable(){
        PersonObserval myfirstHy = new PersonObserval(HystrixObservableCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("myfirstHy")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(2000)), restTemplate(), 1l);
        Observable<String> observe = myfirstHy.observe();
        System.out.println("--------------");
        final String[] reslut = {""};
        observe.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("执行结束");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                   reslut[0] =s;
            }
        });

        return reslut[0];
    }
    @RequestMapping("/xiaofeizhe-agin2-observable-yibu")
    public String observableYibu(){
        PersonObserval myfirstHy = new PersonObserval(HystrixObservableCommand.Setter.withGroupKey(
                HystrixCommandGroupKey.Factory.asKey("myfirstHy")).andCommandPropertiesDefaults(
                HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(2000)), restTemplate(), 1l);
            Observable<String> observe = myfirstHy.toObservable();
        System.out.println("--------------");
        final String[] reslut = {""};
        observe.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("执行结束");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                reslut[0] =s;
            }
        });

        return reslut[0];
    }
    @RequestMapping("/xiaofeizhe-agin2-observable-zhujie-tongbu")
    public String tongbueObservableTongbu(){
        Observable<String> stringObservable = helloService.HystrixCommandTongbu();
        System.out.println("--------------");
        final String[] reslut = {""};
        stringObservable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("执行结束");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                reslut[0] =s;
            }
        });

        return reslut[0];
    }
    @RequestMapping("/xiaofeizhe-agin2-observable-zhujie-yibu")
    public String zhujieObservableYibu(){
        Observable<String> stringObservable = helloService.HystrixCommandYibu();
        System.out.println("--------------");
        final String[] reslut = {""};
        stringObservable.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                System.out.println("执行结束");
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                reslut[0] =s;
            }
        });

        return reslut[0];
    }
    //HystrixCommand 开启缓存功能
    @RequestMapping("xiaofeizhe-duanluqi-huancunOn")
    public String huancunOn(){
        String s = helloService.openCacheByAnnotation1(1L);
        String s1 = helloService.openCacheByAnnotation1(1L);
        helloService.flushCacheByAnnotation1(1L);
        String s2 = helloService.openCacheByAnnotation1(1L);
        return s;
    }
    //HystrixCommand关闭+缓存功能
    @RequestMapping("xiaofeizhe-duanluqi-huancunOff")
    public String huancunOff(){
      helloService.flushCacheByAnnotation1(1L);
      return "关闭缓存成功";
    }
}
