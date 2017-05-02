package com.neworin.easynotes.service;

import com.neworin.easynotes.model.NoteBook;
import com.neworin.easynotes.model.User;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2017/4/30.
 * Project Name:EasyNotesServer
 */
public interface INoteBookService {

    int syncNoteBook(List<NoteBook> list);

    List<NoteBook> getNoteBookByUserId(long userId);

    void updateNoteBook(NoteBook noteBook);

    void insertNoteBook(NoteBook noteBook);

    void handleNoteBook(NoteBook nb);
}
