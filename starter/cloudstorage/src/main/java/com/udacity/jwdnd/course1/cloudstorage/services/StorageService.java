package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.config.StorageProperties;
import com.udacity.jwdnd.course1.cloudstorage.exceptions.StorageException;
import com.udacity.jwdnd.course1.cloudstorage.exceptions.StorageFileNotFoundException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

@Service
public class StorageService {

    private final Path rootLocation;
private final FileMapper fileMapper;
    private UserMapper userMapper;

    @Autowired
    public StorageService(StorageProperties properties, FileMapper fileMapper, UserMapper userMapper) {
        this.rootLocation = Paths.get(properties.getLocation());
        this.fileMapper = fileMapper;
        this.userMapper = userMapper;
    }
    public void init() {
        try {
            Files.createDirectories(rootLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }
    public void store(MultipartFile file, String name) {
        try {
            //MultipartFile file = fileModel.getFiledata();
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

    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.rootLocation, 1)
                    .filter(path -> !path.equals(this.rootLocation))
                    .map(this.rootLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }

    }

    public FileModel getFile(Integer fileId, Authentication authentication){
        FileModel fileModel = fileMapper.getFile(fileId);
        if (fileModel == null)
            return new FileModel(-1,null,null,null,null,null);
        if(userMapper.getUserID(authentication.getName()) != fileModel.getUserid()){
            return new FileModel(-2,null,null,null,null,null);
        }
        else return fileModel;
    }


    public int deleteFile(Integer fileId, Authentication authentication) {
        if(fileMapper.getFile(fileId) == null)
            return -1;
        if(userMapper.getUserID(authentication.getName()) != fileMapper.getFile(fileId).getUserid())
            return -2;
        fileMapper.delete(fileId);
        return 0;
    }
}