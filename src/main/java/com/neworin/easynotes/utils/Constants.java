package com.neworin.easynotes.utils;

/**
 * Created by NewOrin Zhang on 2017/4/22.
 * Project Name:EasyNotesServer
 */
public class Constants {

    public static final String SUCCESS = "SUCCESS";
    public static final String FAILED = "FAILED";

    public static final String AVATAR_FILE_PATH = "H:\\EasyNotes\\avatar";

    public static final String IMAGE_FILE_PATH = "H:\\EasyNotes\\images";
    public static final String IMAGE_FILE_COMPRESS20 = "H:\\EasyNotes\\images_compress20";
    public static final String IMAGE_FILE_COMPRESS40 = "H:\\EasyNotes\\images_compress40";

    public static final String DEFAULT_AVATAR_PATH = "H:\\EasyNotes\\avatar\\default_avatar.png";

    public static final int STATUS_DEFAULT = 0; //默认
    public static final int STATUS_ADD = 2; //新增
    public static final int STATUS_DELETE = -1; //删除
    public static final int STATUS_UPDATE = 1; //更新
    public static final int STATUS_COMPLETED = 9;//同步完成
    public static final int STATUS_FAILED = -9;//同步失败
}