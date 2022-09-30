package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exceptions.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Objects;

@Service
public class StorageService {

    private final FileMapper fileMapper;
    private final UserMapper userMapper;

    @Autowired
    public StorageService(FileMapper fileMapper, UserMapper userMapper) {
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }

    public void store(MultipartFile file, String name) {
        try {
            if (file.isEmpty()) {
                throw new StorageException("Failed to store empty file.");
            }
                fileMapper.insert(new FileModel(null,
                        file.getOriginalFilename(),
                        file.getContentType(),
                        String.valueOf(file.getSize()),
                        userMapper.getUser(name).getUserId()
                        ,file.getInputStream().readAllBytes() ));
        }
        catch (IOException e) {
            throw new StorageException("Failed to store file.", e);
        }
    }

    public FileModel getFile(Integer fileId, Authentication authentication){
        FileModel fileModel = fileMapper.getFile(fileId);
        if (fileModel == null)
            return new FileModel(-1,null,null,null,null,null);
        if(!Objects.equals(userMapper.getUserID(authentication.getName()), fileModel.getUserid())){
            return new FileModel(-2,null,null,null,null,null);
        }
        else return fileModel;
    }


    public int deleteFile(Integer fileId, Authentication authentication) {
        if(fileMapper.getFile(fileId) == null)
            return -1;
        if(!Objects.equals(userMapper.getUserID(authentication.getName()), fileMapper.getFile(fileId).getUserid()))
            return -2;
        fileMapper.delete(fileId);
        return 0;
    }
}