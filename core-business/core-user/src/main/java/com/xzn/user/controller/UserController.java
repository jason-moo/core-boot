package com.xzn.user.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xuezn
 * @Date 2019年07月30日 14:58:12
 */
@RestController
public class UserController {

    @RequestMapping("/user")
    public String login(@RequestParam("userName") String userName){

        System.out.println(userName + "登陆成功");

        return "OK";
    }

}
