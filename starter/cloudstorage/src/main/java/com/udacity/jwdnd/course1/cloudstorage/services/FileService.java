package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {
    private final FileMapper fileMapper;
    private final UserMapper userMapper;
    public FileService(FileMapper fileMapper, UserMapper userMapper){
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }
    public List<FileModel> getFiles(String username){
        int userid = userMapper.getUserID(username);
        return fileMapper.getUserFiles(userid);
    }
}
