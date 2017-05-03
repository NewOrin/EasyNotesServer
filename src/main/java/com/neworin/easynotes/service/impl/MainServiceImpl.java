package com.neworin.easynotes.service.impl;

import com.alibaba.fastjson.JSON;
import com.neworin.easynotes.model.Note;
import com.neworin.easynotes.model.NoteAndBookList;
import com.neworin.easynotes.model.NoteBook;
import com.neworin.easynotes.service.IMainService;
import com.neworin.easynotes.service.INoteBookService;
import com.neworin.easynotes.service.INoteService;
import com.neworin.easynotes.utils.Constants;
import com.neworin.easynotes.utils.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by NewOrin Zhang on 2017/5/1.
 * Project Name:EasyNotesServer
 */
@Service
public class MainServiceImpl implements IMainService {

    @Resource
    private INoteBookService mNoteBookServiceImpl;
    @Resource
    private INoteService mNoteServiceImpl;

    private Logger mLogger = LoggerFactory.getLogger(MainServiceImpl.class);

    /**
     * 处理App请求数据用于更新服务器
     *
     * @param params
     * @return
     */
    public String handlePostData(String params) {
        mLogger.debug("handlePostData = " + params);
        NoteAndBookList noteAndBookList = JSON.parseObject(params, NoteAndBookList.class);
        List<NoteBook> noteBookList = noteAndBookList.getNotebooks();
        List<Note> noteList = noteAndBookList.getNotes();
        long userId = noteAndBookList.getUser_id();
        List<NoteBook> dbNoteBookList = mNoteBookServiceImpl.getNoteBookByUserId(userId);
        List<Note> dbNoteList = mNoteServiceImpl.getNoteByUserId(userId);
        //处理NoteBook数据
        handleNoteBook(noteBookList, dbNoteBookList);
        //处理Note数据
        handleNote(noteList, dbNoteList);
        List<NoteBook> resultNoteBooks = getDiffNoteBooks(noteBookList, dbNoteBookList);
        List<Note> resultNotes = getDiffNotes(noteList, dbNoteList);
        NoteAndBookList nab = new NoteAndBookList(resultNoteBooks, resultNotes, userId);
        return ResponseUtil.parseSuccessRespJson(nab);
    }

    /**
     * 处理App同步数据
     *
     * @param params
     * @return
     */
    public String syncData(String params) {
        mLogger.debug("syncData = " + params);
        NoteAndBookList noteAndBookList = JSON.parseObject(params, NoteAndBookList.class);
        mNoteBookServiceImpl.syncNoteBook(noteAndBookList.getNotebooks());
        mNoteServiceImpl.syncNote(noteAndBookList.getNotes());
        return ResponseUtil.parseSuccessRespJson(Constants.STATUS_COMPLETED);
    }

    /**
     * 处理NoteBook数据
     *
     * @param noteBooks
     * @param dbNoteBooks
     */
    private void handleNoteBook(List<NoteBook> noteBooks, List<NoteBook> dbNoteBooks) {
        for (NoteBook nb : noteBooks) {
            if (!nb.getId().equals(1L)) {
                if (isInNoteBook(nb, dbNoteBooks)) {
                    mNoteBookServiceImpl.handleNoteBook(nb);
                } else {
                    nb.setStatus(Constants.STATUS_COMPLETED);
                    mNoteBookServiceImpl.insertNoteBook(nb);
                }
            }
        }
    }

    /**
     * 处理Note数据
     *
     * @param notes
     * @param dbNotes
     */
    private void handleNote(List<Note> notes, List<Note> dbNotes) {
        for (Note note : notes) {
            if (isInNote(note, dbNotes)) {
                mNoteServiceImpl.handleNote(note);
            } else {
                note.setStatus(Constants.STATUS_COMPLETED);
                mNoteServiceImpl.insertNote(note);
            }
        }
    }

    /**
     * 判断NoteBook是否存在于数据库中
     *
     * @param noteBook
     * @param dbNoteBooks
     * @return
     */
    private Boolean isInNoteBook(NoteBook noteBook, List<NoteBook> dbNoteBooks) {
        for (NoteBook dbNoteBook : dbNoteBooks) {
            if (noteBook.getId().equals(dbNoteBook.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 判断Note是否存在于数据库中
     *
     * @param note
     * @param dbNotes
     * @return
     */
    private Boolean isInNote(Note note, List<Note> dbNotes) {
        for (Note dbNote : dbNotes) {
            if (note.getId().equals(dbNote.getId())) {
                return true;
            }
        }
        return false;
    }

    /**
     * 返回NoteBook数据库中有的数据而App中没有的
     *
     * @param noteBooks
     * @param dbNoteBooks
     * @return
     */
    private List<NoteBook> getDiffNoteBooks(List<NoteBook> noteBooks, List<NoteBook> dbNoteBooks) {
        List<NoteBook> resultList = new ArrayList<NoteBook>();
        for (NoteBook dbNoteBook : dbNoteBooks) {
            for (NoteBook nb : noteBooks) {
                if (!dbNoteBook.getId().equals(nb.getId())) {
                    resultList.add(dbNoteBook);
                }
            }
        }
        return resultList;
    }

    /**
     * 返回Note数据库中有的数据而App中没有的
     *
     * @param notes
     * @param dbNotes
     * @return
     */
    private List<Note> getDiffNotes(List<Note> notes, List<Note> dbNotes) {
        List<Note> resultList = new ArrayList<Note>();
        if (notes.size() == 0) {
            return dbNotes;
        }
        for (Note dbNote : dbNotes) {
            for (Note n : notes) {
                if (!n.getId().equals(dbNote.getId())) {
                    resultList.add(dbNote);
                }
            }
        }
        return resultList;
    }

}