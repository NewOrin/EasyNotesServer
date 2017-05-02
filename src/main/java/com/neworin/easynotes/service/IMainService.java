package com.neworin.easynotes.service;

/**
 * Created by NewOrin Zhang on 2017/5/1.
 * Project Name:EasyNotesServer
 */
public interface IMainService {

    public String handlePostData(String params);

    public String syncData(String params);
}
