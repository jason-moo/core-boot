package com.xzn.shop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @Author xuezn
 * @Date 2019年07月30日 14:34:08
 */
@SpringBootApplication
@EnableEurekaClient
public class ShopApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShopApplication.class);
    }

}
