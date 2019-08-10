package com.xzn.shop.service.impl;

import com.xzn.shop.service.ShopService;
import com.xzn.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private UserService userService;

    @Override
    public void sayHello() {
        System.out.println(userService.login("aaaa"));
        System.out.println("Hello");
    }

}
