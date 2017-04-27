package com.neworin.easynotes.service.impl;

import com.neworin.easynotes.dao.UserMapper;
import com.neworin.easynotes.model.User;
import com.neworin.easynotes.model.UserExample;
import com.neworin.easynotes.service.IFileService;
import com.neworin.easynotes.utils.Constants;
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

    @Resource
    private UserMapper mUserMapper;

    public String fileSave(MultipartFile file, String desc) {
        String fileName = file.getOriginalFilename(); // 处理保存的文件名
        System.out.println("getOriginalFilename --->" + file.getOriginalFilename()); // 文件名
        File destFile = new File(Constants.AVATAR_FILE_PATH, fileName); // 创建要保存的文件
        if (destFile.exists()) {
            destFile.delete();
        }
        destFile.mkdirs();
        // 保存操作
        try {
            file.transferTo(destFile); // 另存为
            System.out.println("文件保存成功:" + Constants.AVATAR_FILE_PATH + "\\" + fileName);
            User user = mUserMapper.selectByEmail(desc);
            user.setAvatarurl(Constants.AVATAR_FILE_PATH + "\\" + fileName);
            UserExample userExample = new UserExample();
            UserExample.Criteria criteria = userExample.createCriteria();
            criteria.andIdEqualTo(user.getId());
            mUserMapper.updateByExample(user, userExample);
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return "文件保存失败";
        } catch (IOException e) {
            e.printStackTrace();
            return "文件保存失败";
        }
        return Constants.SUCCESS;
    }

    public File getUserAvatar(String email) {
        User user = mUserMapper.selectByEmail(email);
        String path;
        if (null == user || user.getAvatarurl().equals("")) {
            path = Constants.DEFAULT_AVATAR_PATH;
        } else {
            path = user.getAvatarurl();
        }
        return new File(path);
    }
}
