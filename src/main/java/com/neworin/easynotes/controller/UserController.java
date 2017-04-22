package com.neworin.easynotes.controller;

import com.neworin.easynotes.model.User;
import com.neworin.easynotes.service.IUserService;
import com.neworin.easynotes.utils.DateUtil;
import com.neworin.easynotes.utils.ResponseUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by NewOrin Zhang on 2017/4/22.
 * Project Name:EasyNotesServer
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService mUserServiceImpl;

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String userLogin(@RequestBody String params) {
        User user = mUserServiceImpl.userLogin(params);
        if (null == user) {
            return ResponseUtil.parseFailedRespJson("邮箱或密码错误");
        }
        user.setJointime(null);
        return ResponseUtil.parseSuccessRespJson(user);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String userRegister(@RequestBody String params) {
        return mUserServiceImpl.userRegister(params);
    }
}