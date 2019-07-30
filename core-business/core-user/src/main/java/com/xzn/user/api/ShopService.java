package com.xzn.user.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author xuezn
 * @Date 2019年07月30日 15:18:26
 */
@FeignClient(name = "SHOP-SERVICE")
public interface ShopService {

    @RequestMapping("/buy")
    String buy(@RequestParam("goodName") String goodName);

}
