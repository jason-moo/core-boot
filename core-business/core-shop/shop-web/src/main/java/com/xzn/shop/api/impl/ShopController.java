package com.xzn.shop.api.impl;

import com.xzn.shop.api.ShopService;
import com.xzn.shop.dto.GoodsDTO;
import com.xzn.user.api.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @Author xuezn
 * @Date 2019年07月30日 15:06:00
 */
@RestController
public class ShopController implements ShopService {

    @Autowired
    private UserService userService;

    @Override
    public String buy(@RequestParam("goodName") String goodName,@RequestParam("userName") String userName){

        userService.login(userName);

        System.out.println(goodName + "购买成功");

        return "OK";
    }

    @Override
    public String buy(@RequestParam("goodName") String goodName){

        System.out.println(goodName + "购买成功");

        return "OK";
    }


    @Override
    public String buySomeGoods(Map<String, Object> map) {
        return null;
    }

    @Override
    public String buyGoods(GoodsDTO goodsDTO) {
        return null;
    }
}
