package com.xzn.user.api.impl;

import com.xzn.redis.utils.RedisUtils;
import com.xzn.shop.api.ShopRemoteService;
import com.xzn.user.api.UserRemoteService;
import com.xzn.user.entity.User;
import com.xzn.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xuezn
 * @Date 2019年07月30日 14:58:12
 */
@RestController
public class UserRemoteController implements UserRemoteService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ShopRemoteService shopRemoteService;

    @Autowired
    private UserService userService;

    @Override
    public String login(@RequestParam("userName") String userName){
        User user = userService.selectById(1);
        RedisUtils.put("sdadsad",user);
        System.out.println(userName + "登陆成功");
        return "OK";
    }

    @Override
    public String login2(@RequestParam("userName") String userName){

        System.out.println(userName + "登陆成功");

        return "OK2";
    }

}
