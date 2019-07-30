package com.xzn.shop.controller;

import com.xzn.shop.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xuezn
 * @Date 2019年07月30日 15:06:00
 */
@RestController
public class ShopController {

    @Autowired
    private UserService userService;

    @RequestMapping("/buy")
    public String buy(@RequestParam("goodName") String goodName,@RequestParam("userName") String userName){

        userService.login(userName);

        System.out.println(goodName + "购买成功");

        return "OK";
    }

    @RequestMapping("/buy")
    public String buy(@RequestParam("goodName") String goodName){

        System.out.println(goodName + "购买成功");

        return "OK";
    }
}
