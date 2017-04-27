package com.neworin.easynotes.controller;

import com.neworin.easynotes.service.IFileService;
import com.neworin.easynotes.utils.Constants;
import com.neworin.easynotes.utils.ResponseUtil;
import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
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
        String result = mFileServiceImpl.fileSave(file, description);
        if (!result.equals(Constants.SUCCESS)) {
            return ResponseUtil.parseFailedRespJson(result);
        }
        return ResponseUtil.parseSuccessRespJson();
    }

    @RequestMapping(value = "/get_user_avatar/{user_id}")
    public ResponseEntity<byte[]> fileDownload(@PathVariable String user_id) throws IOException {
        File file = mFileServiceImpl.getUserAvatar(user_id);
        System.out.println("file_name = " + file.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", file.getName());
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);
    }
}