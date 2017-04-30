package com.neworin.easynotes.dao;

import com.neworin.easynotes.model.NoteBook;
import com.neworin.easynotes.model.NoteBookExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoteBookMapper {
    int countByExample(NoteBookExample example);

    int deleteByExample(NoteBookExample example);

    int deleteByPrimaryKey(Long id);

    int insert(NoteBook record);

    int insertSelective(NoteBook record);

    List<NoteBook> selectByExample(NoteBookExample example);

    NoteBook selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NoteBook record, @Param("example") NoteBookExample example);

    int updateByExample(@Param("record") NoteBook record, @Param("example") NoteBookExample example);

    int updateByPrimaryKeySelective(NoteBook record);

    int updateByPrimaryKey(NoteBook record);
}