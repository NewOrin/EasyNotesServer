package com.neworin.easynotes.service.impl;

import com.neworin.easynotes.dao.NoteImageMapper;
import com.neworin.easynotes.model.NoteImage;
import com.neworin.easynotes.service.INoteImageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by NewOrin Zhang on 2017/5/14.
 * Project Name:EasyNotesServer
 */
@Service
public class NoteImageServiceImpl implements INoteImageService {

    @Resource
    private NoteImageMapper mNoteImageMapper;

    public void insertImage(NoteImage noteImage) {
        mNoteImageMapper.insert(noteImage);
    }
}
