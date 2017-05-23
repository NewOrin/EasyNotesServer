package com.neworin.easynotes.service.impl;

import com.alibaba.fastjson.JSON;
import com.neworin.easynotes.dao.FeedBackMapper;
import com.neworin.easynotes.model.FeedBack;
import com.neworin.easynotes.service.IFeedBackService;
import com.neworin.easynotes.utils.ResponseUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by NewOrin Zhang on 2017/5/23.
 * Project Name:EasyNotesServer
 */
@Service
public class FeedBackServiceImpl implements IFeedBackService {

    @Resource
    private FeedBackMapper mFeedBackMapper;

    public String addFeedBack(String params) {
        FeedBack feedBack = JSON.parseObject(params, FeedBack.class);
        mFeedBackMapper.insert(feedBack);
        return ResponseUtil.parseSuccessRespJson();
    }
}
