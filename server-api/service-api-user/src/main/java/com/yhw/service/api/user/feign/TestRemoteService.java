package com.yhw.service.api.user.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(contextId="test",value = "test")
public interface TestRemoteService {

    @GetMapping("/getTest")
    String test();

}
