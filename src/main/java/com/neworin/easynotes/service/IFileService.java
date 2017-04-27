package com.neworin.easynotes.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;

/**
 * Created by NewOrin Zhang on 2017/4/26.
 * Project Name:EasyNotesServer
 */
public interface IFileService {

    String fileSave(MultipartFile file, String desc);

    File getUserAvatar(String email);
}
