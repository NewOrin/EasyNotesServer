package com.neworin.easynotes.dao;

import com.neworin.easynotes.model.NoteImage;
import com.neworin.easynotes.model.NoteImageExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface NoteImageMapper {
    int countByExample(NoteImageExample example);

    int deleteByExample(NoteImageExample example);

    int insert(NoteImage record);

    int insertSelective(NoteImage record);

    List<NoteImage> selectByExample(NoteImageExample example);

    int updateByExampleSelective(@Param("record") NoteImage record, @Param("example") NoteImageExample example);

    int updateByExample(@Param("record") NoteImage record, @Param("example") NoteImageExample example);
}