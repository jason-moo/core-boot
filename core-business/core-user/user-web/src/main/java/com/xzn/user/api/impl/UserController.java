package com.xzn.user.api.impl;

import com.xzn.redis.utils.RedisUtils;
import com.xzn.shop.api.ShopService;
import com.xzn.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author xuezn
 * @Date 2019年07月30日 14:58:12
 */
@RestController
public class UserController implements UserService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private ShopService shopService;

    @Override
    public String login(@RequestParam("userName") String userName){
        RedisUtils.put("sdadsad","dadadsa");
        System.out.println(userName + "登陆成功");
        return "OK";
    }

    @Override
    public String login2(@RequestParam("userName") String userName){

        System.out.println(userName + "登陆成功");

        return "OK2";
    }

}
