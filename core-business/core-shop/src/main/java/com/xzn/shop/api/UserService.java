package com.xzn.shop.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author xuezn
 * @Date 2019年07月30日 15:08:14
 */

@FeignClient(name = "USER-SERVICE")
public interface UserService {

    @RequestMapping("/user")
    String login(@RequestParam("userName") String userName);

}
