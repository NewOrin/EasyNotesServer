package com.neworin.easynotes.controller;

import com.neworin.easynotes.service.INoteService;
import com.neworin.easynotes.utils.Constants;
import com.neworin.easynotes.utils.ResponseUtil;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by NewOrin Zhang on 2017/4/30.
 * Project Name:EasyNotesServer
 */
@RestController
@RequestMapping("/note")
public class NoteController {

    @Resource
    private INoteService mNoteServiceImpl;

    @RequestMapping(value = "/sync_data", method = RequestMethod.POST, produces = "text/html;charset=UTF-8")
    public String syncData(@RequestBody String params) {
//        int result_code = mNoteServiceImpl.syncNote(params);
//        if (result_code == Constants.STATUS_FAILED) {
//            return ResponseUtil.parseFailedRespJson();
//        }
        return ResponseUtil.parseSuccessRespJson();
    }
}
