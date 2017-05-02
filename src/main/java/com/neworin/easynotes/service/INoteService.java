package com.neworin.easynotes.service;

import com.neworin.easynotes.model.Note;

import java.util.List;

/**
 * Created by NewOrin Zhang on 2017/4/30.
 * Project Name:EasyNotesServer
 */
public interface INoteService {

    int syncNote(List<Note> list);

    List<Note> getNoteByUserId(long userId);

    void insertNote(Note note);

    void handleNote(Note note);
}
