package com.neworin.easynotes.service.impl;

import com.alibaba.fastjson.JSON;
import com.neworin.easynotes.dao.NoteImageMapper;
import com.neworin.easynotes.dao.UserMapper;
import com.neworin.easynotes.model.NoteImage;
import com.neworin.easynotes.model.User;
import com.neworin.easynotes.model.UserExample;
import com.neworin.easynotes.service.IFileService;
import com.neworin.easynotes.service.INoteImageService;
import com.neworin.easynotes.utils.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;

/**
 * Created by NewOrin Zhang on 2017/4/26.
 * Project Name:EasyNotesServer
 */
@Service
public class FileServiceImpl implements IFileService {

    Logger mLogger = LoggerFactory.getLogger(FileServiceImpl.class);
    @Resource
    private UserMapper mUserMapper;
    @Resource
    private INoteImageService mNoteImageServiceImpl;

    public String avatarSave(MultipartFile file, String desc) {
        String fileName = file.getOriginalFilename(); // 处理保存的文件名
        mLogger.debug("用户id = " + desc + " 的文件保存 = " + fileName);
        File destFile = new File(Constants.AVATAR_FILE_PATH, fileName); // 创建要保存的文件
        if (destFile.exists()) {
            mLogger.debug("文件已存在需删除");
            destFile.delete();
        }
        destFile.mkdirs();
        // 保存操作
        try {
            file.transferTo(destFile); // 另存为
            mLogger.debug("文件保存成功 : " + Constants.AVATAR_FILE_PATH + "\\" + fileName);
            User user = mUserMapper.selectByPrimaryKey(Long.parseLong(desc));
            user.setAvatarurl(Constants.AVATAR_FILE_PATH + "\\" + fileName);
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andIdEqualTo(user.getId());
            mUserMapper.updateByExample(user, userExample);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            mLogger.debug("文件保存失败 " + e.getMessage());
            return "文件保存失败";
        } catch (IOException e) {
            e.printStackTrace();
            mLogger.debug("文件保存失败 " + e.getMessage());
            return "文件保存失败";
        }
        return Constants.SUCCESS;
    }

    public File getUserAvatar(String user_id) {
        User user = mUserMapper.selectByPrimaryKey(Long.parseLong(user_id));
        mLogger.debug("用户id= " + user_id + " 获取头像");
        String path;
        if (null == user.getAvatarurl()) {
            mLogger.debug("该用户无头像，使用默认头像");
            path = Constants.DEFAULT_AVATAR_PATH;
        } else {
            mLogger.debug("用户头像url = " + user.getAvatarurl());
            path = user.getAvatarurl();
        }
        return new File(path);
    }

    public String noteImageSave(MultipartFile file, String desc) {
        String fileName = file.getOriginalFilename(); // 处理保存的文件名
        mLogger.debug("用户id = " + desc + " 的文件保存 = " + fileName);
        File destFile = new File(Constants.IMAGE_FILE_PATH, fileName); // 创建要保存的文件
        if (destFile.exists()) {
            mLogger.debug("文件已存在无需保存");
            return Constants.SUCCESS;
        }
        destFile.mkdirs();
        try {
            file.transferTo(destFile); // 另存为
            mLogger.debug("文件保存成功 : " + Constants.IMAGE_FILE_PATH + "\\" + fileName);
            mNoteImageServiceImpl.insertImage(new NoteImage(Long.parseLong(desc), fileName));
        } catch (IOException e) {
            e.printStackTrace();
            mLogger.debug("文件保存失败 " + e.getMessage());
        }
        return Constants.SUCCESS;
    }
}
