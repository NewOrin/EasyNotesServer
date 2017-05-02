package com.neworin.easynotes.service.impl;

import com.google.gson.Gson;
import com.neworin.easynotes.dao.NoteBookMapper;
import com.neworin.easynotes.dao.NoteMapper;
import com.neworin.easynotes.model.Note;
import com.neworin.easynotes.model.NoteBook;
import com.neworin.easynotes.service.INoteService;
import com.neworin.easynotes.utils.Constants;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by NewOrin Zhang on 2017/4/30.
 * Project Name:EasyNotesServer
 */
@Service
public class NoteServiceImpl implements INoteService {

    @Resource
    private NoteMapper mNoteMapper;

    /**
     * 同步笔记
     *
     * @param list
     * @return
     */
    public int syncNote(List<Note> list) {
        syncData(list);
        return Constants.STATUS_COMPLETED;
    }

    /**
     * 解析数组同步数据
     *
     * @param noteList
     */
    private void syncData(List<Note> noteList) {
        for (Note n : noteList) {
            handleNote(n);
        }
    }

    public List<Note> getNoteByUserId(long userId) {
        return mNoteMapper.selectByUserId(userId);
    }

    public void insertNote(Note note) {
        mNoteMapper.insert(note);
    }

    /**
     * 处理Note
     *
     * @param note
     */
    public void handleNote(Note note) {
        switch (note.getStatus()) {
            case Constants.STATUS_ADD:
                note.setStatus(Constants.STATUS_COMPLETED);
                mNoteMapper.insert(note);
                break;
            case Constants.STATUS_DELETE:
                note.setStatus(Constants.STATUS_COMPLETED);
                note.setIsDelete(1);
                mNoteMapper.updateByPrimaryKey(note);
                break;
            case Constants.STATUS_UPDATE:
                note.setStatus(Constants.STATUS_COMPLETED);
                mNoteMapper.updateByPrimaryKey(note);
                break;
            case Constants.STATUS_COMPLETED:
                note.setStatus(Constants.STATUS_COMPLETED);
                break;
            default:
                break;
        }
    }
}
