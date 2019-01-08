package com.cloudlearn.jichengfeign3.service;

import com.cloudlearn.hellojicheng.controller.UserController;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

@Component
@FeignClient("hello-jicheng-server")
public interface FeignService extends UserController {

}
