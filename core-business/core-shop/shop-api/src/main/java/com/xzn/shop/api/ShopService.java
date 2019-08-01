package com.xzn.shop.api;

import com.xzn.shop.dto.GoodsDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * @Author xuezn
 * @Date 2019年07月31日 13:52:26
 */
@FeignClient(value = "SHOP-SERVICE")
public interface ShopService {

    @RequestMapping(value = "/buy" ,method = RequestMethod.GET)
    String buy(@RequestParam("goodName") String goodName, @RequestParam("userName") String userName);

    @RequestMapping(value = "/buyGood",method = RequestMethod.GET)
    String buy(@RequestParam("goodName") String goodName);

    @RequestMapping(value = "/buySomeGoods",method = RequestMethod.GET)
    String buySomeGoods(@RequestParam Map<String,Object> map);

    @RequestMapping(value = "/buyGood",method = RequestMethod.POST)
    String buyGoods(@RequestBody GoodsDTO goodsDTO);

}
