package com.springcloudlearn.busconfig;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
public class GitController {
   @Value("${from}")
    private String from;
   @RequestMapping("/from")
    public String from(){
       return  from;
   }
}
