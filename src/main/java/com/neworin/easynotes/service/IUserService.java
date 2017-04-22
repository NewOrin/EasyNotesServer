package com.neworin.easynotes.service;

import com.neworin.easynotes.model.User;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2017/4/22.
 * Project Name:EasyNotesServer
 */
public interface IUserService {

    User userLogin(String params);

    String userRegister(String params);
}
