package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    @Select("SELECT * FROM FILES WHERE USERID = #{userid}")
    List<FileModel> getUserFiles(int userid);

    @Select("SELECT * FROM FILES WHERE FILEID = #{fileid}")
    FileModel getFile(int fileid);

    @Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES(#{fileName}, #{contenttype}, #{filesize}, #{userid}, #{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileId")
    int insert(FileModel fileModel);

    @Delete("DELETE FROM FILES WHERE FILEID = #{fileId}")
    void delete(int fileId);
}
