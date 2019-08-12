package com.xzn.user.controller;

import com.xzn.core.vo.Result;
import com.xzn.user.entity.User;
import com.xzn.user.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public Result<User> getUserInfo(){
        User user = userService.selectById(1L);
        log.info("At the age of {} I go to school.", user);
        Result<User> result = new Result<>(user);
        return result;
    }

}
