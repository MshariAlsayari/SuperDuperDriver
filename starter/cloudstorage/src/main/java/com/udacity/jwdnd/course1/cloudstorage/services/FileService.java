package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileService {

    private final FileMapper fileMapper;
    private final EncryptionService encryptionService;
    private final String FILE_SALT = "FileSalt";

    public FileService(FileMapper fileMapper, EncryptionService encryptionService) {
        this.fileMapper = fileMapper;
        this.encryptionService = encryptionService;
    }

    public boolean isFileAvailable(String fileName) {
        return fileMapper.getFile(fileName) == null;
    }

    public int createFile(File file) {
        return fileMapper.insertFile(new File(null, file.getFilename(), file.getContentType(), file.getFileSize(), file.getUserid(), file.getFiledata()));
    }

    public  List<File> getFiles(){
        return fileMapper.getAllFile();
    }

    public  File getFile(String fileName){
        return fileMapper.getFile(fileName);
    }

    public  void deleteFile(Integer fileId){
        fileMapper.deleteFile(fileId);
    }


}
