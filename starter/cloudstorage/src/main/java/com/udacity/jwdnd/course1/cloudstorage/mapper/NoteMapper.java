package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NoteMapper {
    @Select("SELECT * FROM NOTES WHERE USERID = #{userid}")
    List<NoteModel> getUserNotes(int userid);
    @Select("SELECT * FROM NOTES WHERE NOTEID = #{noteid}")
    NoteModel getNote(int noteid);

    @Insert("INSERT INTO NOTES (notetitle, notedescription, userid) VALUES(#{noteTitle}, #{noteDescription}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insertNote(NoteModel noteModel);

    @Delete("DELETE FROM NOTES WHERE NOTEID = #{noteid}")
    void deleteNote(int noteid);

    @Update("UPDATE NOTES SET NOTETITLE = #{noteTitle} , NOTEDESCRIPTION = #{noteDescription} WHERE NOTEID = #{noteid}")
    void updateNote(NoteModel noteModel);
}
