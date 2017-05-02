package com.neworin.easynotes.controller;

import com.neworin.easynotes.service.IMainService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by NewOrin Zhang on 2017/5/1.
 * Project Name:EasyNotesServer
 */
@RestController
@RequestMapping("/main")
public class MainController {

    @Resource
    private IMainService mMainServiceImpl;

    @RequestMapping(value = "/post_data", produces = "text/html;charset=UTF-8")
    public String getPostNoteData(@RequestBody String params) {
        return mMainServiceImpl.handlePostData(params);
    }

    @RequestMapping(value = "/sync_data", produces = "text/html;charset=UTF-8")
    public String syncData(@RequestBody String params) {
        return mMainServiceImpl.syncData(params);
    }
}
