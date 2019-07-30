package com.xzn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author xuezn
 * @Date 2019年07月30日 14:34:08
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.xzn.shop.api")
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class);
    }

}
