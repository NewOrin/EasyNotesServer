package com.neworin.easynotes.controller;

import com.neworin.easynotes.service.IFileService;
import com.neworin.easynotes.utils.Constants;
import com.neworin.easynotes.utils.ResponseUtil;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.List;

/**
 * Created by NewOrin Zhang on 2017/4/24.
 * Project Name:EasyNotesServer
 */
@RestController
@RequestMapping("/file")
public class FileController {

    Logger mLogger = LoggerFactory.getLogger(FileController.class);

    @Resource
    private IFileService mFileServiceImpl;

    @RequestMapping("/file_upload")
    public String fileUpload(@RequestParam("file") MultipartFile file, @RequestParam String description, HttpServletRequest request) {
        String result = mFileServiceImpl.avatarSave(file, description);
        if (!result.equals(Constants.SUCCESS)) {
            return ResponseUtil.parseFailedRespJson(result);
        }
        return ResponseUtil.parseSuccessRespJson();
    }

    @RequestMapping("/note_image_upload")
    public String noteImageUpload(@RequestParam("file") List<MultipartFile> file, @RequestParam String description, HttpServletRequest request) {
        for (MultipartFile f : file) {
            String result = mFileServiceImpl.noteImageSave(f, description);
            if (!result.equals(Constants.SUCCESS)) {
                return ResponseUtil.parseFailedRespJson(result);
            }
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

    @RequestMapping(value = "/get_note_image/{image_name}/{quality}")
    public ModelAndView noteImageDownload(HttpServletRequest request, HttpServletResponse response, @PathVariable String image_name, @PathVariable String quality) throws IOException {
        String path = Constants.IMAGE_FILE_PATH + "\\" + image_name;
        String thumbPath;
        float qual = 0;
        if (Integer.parseInt(quality) == 40) {
            thumbPath = Constants.IMAGE_FILE_COMPRESS40 + "\\" + image_name;
            qual = 0.4f;
        } else {
            thumbPath = Constants.IMAGE_FILE_COMPRESS20 + "\\" + image_name;
            qual = 0.2f;
        }
        File file = new File(path);
        if (!file.exists()) {
            mLogger.debug("文件 " + file.getName() + " 不存在");
            return null;
        }
        File thumbFile = new File(thumbPath);
        //若缩略图不存在，则创建
        if (!thumbFile.exists()) {
            Thumbnails.of(file).scale(qual).toFile(thumbFile);
        }
        request.setCharacterEncoding("UTF-8");
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        //获取文件长度
        long fileLength = thumbFile.length();
        //设置文件输出类型
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition", "attachment; filename=" + image_name);
        //设置输出长度
        response.setHeader("Content-Length", String.valueOf(fileLength));
        //获取输入流
        bis = new BufferedInputStream(new FileInputStream(thumbPath));
        //输出流
        bos = new BufferedOutputStream(response.getOutputStream());
        byte[] buff = new byte[2048];
        int bytesRead;
        while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
            bos.write(buff, 0, bytesRead);
        }
        bis.close();
        bos.close();
        return null;
    }
}