package com.neworin.easynotes.controller;

import com.neworin.easynotes.service.IFeedBackService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by NewOrin Zhang on 2017/5/23.
 * Project Name:EasyNotesServer
 */
@RequestMapping("/feedback")
@RestController
public class FeedBackController {

    @Resource
    private IFeedBackService mFeedBackServiceImpl;

    @RequestMapping("/add")
    public String add_feedback(@RequestBody String params) {
        return mFeedBackServiceImpl.addFeedBack(params);
    }
}
