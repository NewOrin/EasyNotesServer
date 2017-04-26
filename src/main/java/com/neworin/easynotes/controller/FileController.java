package com.neworin.easynotes.controller;

import com.neworin.easynotes.service.IFileService;
import com.neworin.easynotes.utils.Constants;
import com.neworin.easynotes.utils.ResponseUtil;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;

/**
 * Created by NewOrin Zhang on 2017/4/24.
 * Project Name:EasyNotesServer
 */
@RestController
@RequestMapping("/file")
public class FileController {

    @Resource
    private IFileService mFileServiceImpl;

    @RequestMapping("/file_upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, @RequestParam String description, HttpServletRequest request) {
        String path = request.getSession().getServletContext().getRealPath("upload");
        String result = mFileServiceImpl.fileSave(file, path, description);
        if (!result.equals(Constants.SUCCESS)) {
            return ResponseUtil.parseFailedRespJson(result);
        }
        return ResponseUtil.parseSuccessRespJson();
    }
}
