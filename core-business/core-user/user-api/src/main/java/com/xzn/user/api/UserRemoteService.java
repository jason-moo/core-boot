package com.xzn.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author xuezn
 * @Date 2019年07月31日 14:00:54
 */
@FeignClient(value = "USER-SERVICE")
public interface UserRemoteService {

    @RequestMapping("/user/login")
    String login(@RequestParam("userName") String userName);

    @RequestMapping("/login")
    String login2(@RequestParam("userName") String userName);
}
