package com.neworin.easynotes.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by NewOrin Zhang on 2017/4/26.
 * Project Name:EasyNotesServer
 */
public interface IFileService {

    String avatarSave(MultipartFile file, String desc);

    File getUserAvatar(String email);

    String noteImageSave(MultipartFile file,String desc);
}
