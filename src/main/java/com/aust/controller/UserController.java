package com.aust.controller;/*
 * Copyright © 2016 睿泰集团 版权所有
 */

import com.aust.bean.User;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * @Autor zhouNan
 * @Date 2019/9/18 13:15
 * @Description UserController
 **/
@RestController
public class UserController {
    @RequestMapping("/user/{id}")
    public Object user(HttpServletRequest request, @PathVariable("id") String id) {
        int localPort = request.getLocalPort();
        return  new User(id ,"name "+ localPort);
    }
}
