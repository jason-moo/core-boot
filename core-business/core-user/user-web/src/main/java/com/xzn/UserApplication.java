package com.xzn;

import org.mybatis.spring.annotation.MapperScan;
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
@MapperScan(basePackages = "com.xzn.user.mapper")
public class UserApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserApplication.class, args);
    }

}
