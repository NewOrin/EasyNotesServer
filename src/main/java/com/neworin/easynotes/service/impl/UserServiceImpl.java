package com.neworin.easynotes.service.impl;

import com.google.gson.Gson;
import com.neworin.easynotes.dao.UserMapper;
import com.neworin.easynotes.model.User;
import com.neworin.easynotes.model.UserExample;
import com.neworin.easynotes.service.IUserService;
import com.neworin.easynotes.utils.DateUtil;
import com.neworin.easynotes.utils.GenerateSequenceUtil;
import com.neworin.easynotes.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by NewOrin Zhang on 2017/4/22.
 * Project Name:EasyNotesServer
 */
@Service
public class UserServiceImpl implements IUserService {

    @Resource
    private UserMapper mUserMapper;

    public User userLogin(String params) {
        Gson gson = new Gson();
        User user = gson.fromJson(params, User.class);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(user.getEmail());
        criteria.andPasswordEqualTo(user.getPassword());
        List<User> userList = mUserMapper.selectByExample(userExample);
        if (userList.size() == 0) {
            return null;
        }
        return userList.get(0);
    }

    public String userRegister(String params) {
        Gson gson = new Gson();
        User user = gson.fromJson(params, User.class);
        UserExample userExample = new UserExample();
        UserExample.Criteria criteria = userExample.createCriteria();
        criteria.andEmailEqualTo(user.getEmail());
        List<User> userList = mUserMapper.selectByExample(userExample);
        if (userList.size() != 0) {
            return ResponseUtil.parseFailedRespJson("该邮箱已注册");
        }
        user.setId(GenerateSequenceUtil.generateSequenceLongNo());
        user.setJointime(DateUtil.getNowTime());
        if (mUserMapper.insert(user) != 1) {
            return ResponseUtil.parseFailedRespJson("注册失败,请重试");
        }
        user.setJointime(null);
        return ResponseUtil.parseSuccessRespJson(user);
    }
}
