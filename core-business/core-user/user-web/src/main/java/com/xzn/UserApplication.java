package com.xzn;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @Author xuezn
 * @Date 2019年07月30日 13:59:52
 */
@SpringBootApplication
@EnableEurekaClient
@EnableFeignClients(basePackages = "com.xzn.**.api")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
