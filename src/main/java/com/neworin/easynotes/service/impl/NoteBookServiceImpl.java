package com.neworin.easynotes.service.impl;

import com.google.gson.Gson;
import com.neworin.easynotes.dao.NoteBookMapper;
import com.neworin.easynotes.model.Note;
import com.neworin.easynotes.model.NoteBook;
import com.neworin.easynotes.model.User;
import com.neworin.easynotes.service.INoteBookService;
import com.neworin.easynotes.utils.Constants;
import com.neworin.easynotes.utils.DateUtil;
import com.neworin.easynotes.utils.GenerateSequenceUtil;
import com.neworin.easynotes.utils.GsonUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NewOrin Zhang on 2017/4/30.
 * Project Name:EasyNotesServer
 */
@Service
public class NoteBookServiceImpl implements INoteBookService {

    private Logger mLogger = LoggerFactory.getLogger(NoteBookServiceImpl.class);

    @Resource
    private NoteBookMapper mNoteBookMapper;

    /**
     * 同步笔记本
     *
     * @param list
     * @return
     */
    public int syncNoteBook(List<NoteBook> list) {
        syncData(list);
        return Constants.STATUS_COMPLETED;
    }

    /**
     * 根据用户id获取notebook
     *
     * @param userId
     * @return
     */
    public List<NoteBook> getNoteBookByUserId(long userId) {

        return mNoteBookMapper.selectByUserId(userId);
    }

    public void updateNoteBook(NoteBook noteBook) {
        mNoteBookMapper.updateByPrimaryKey(noteBook);
    }

    public void insertNoteBook(NoteBook noteBook) {
        mNoteBookMapper.insert(noteBook);
    }

    /**
     * 解析数组同步数据
     *
     * @param noteBookList
     */
    private void syncData(List<NoteBook> noteBookList) {
        for (NoteBook nb : noteBookList) {
            mLogger.debug(nb.toString());
            handleNoteBook(nb);
        }
    }

    /**
     * 处理NoteBook
     *
     * @param nb
     */
    public void handleNoteBook(NoteBook nb) {
        switch (nb.getStatus()) {
            case Constants.STATUS_DEFAULT:
                break;
            case Constants.STATUS_ADD:
                nb.setStatus(Constants.STATUS_COMPLETED);
                mNoteBookMapper.insert(nb);
                break;
            case Constants.STATUS_DELETE:
                nb.setStatus(Constants.STATUS_COMPLETED);
                nb.setIsDelete(1);
                mNoteBookMapper.updateByPrimaryKey(nb);
                break;
            case Constants.STATUS_UPDATE:
                nb.setStatus(Constants.STATUS_COMPLETED);
                mNoteBookMapper.updateByPrimaryKey(nb);
                break;
            case Constants.STATUS_COMPLETED:
                break;
            default:
                break;
        }
    }
}
