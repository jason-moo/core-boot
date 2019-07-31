package com.xzn.shop.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @Author xuezn
 * @Date 2019年07月31日 13:52:26
 */
@FeignClient(value = "SHOP-SERVICE")
public interface ShopService {

    @RequestMapping("/buy")
    String buy(@RequestParam("goodName") String goodName, @RequestParam("userName") String userName);

    @RequestMapping("/buyGood")
    String buy(@RequestParam("goodName") String goodName);

}
