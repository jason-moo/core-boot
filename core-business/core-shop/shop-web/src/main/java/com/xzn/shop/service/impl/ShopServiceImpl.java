package com.xzn.shop.service.impl;

import com.xzn.shop.service.ShopService;
import com.xzn.user.api.UserRemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private UserRemoteService userRemoteService;

    @Override
    public void sayHello() {
        System.out.println(userRemoteService.login("aaaa"));
        System.out.println("Hello");
    }

}
