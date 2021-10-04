package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/home")
public class HomeController {


    private final FileService fileService;
    private final UserService userService;

    public HomeController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @GetMapping
    public  String homeView(Model model){
        model.addAttribute("files", this.fileService.getFiles());
        return "home";
    }

    @PostMapping("files")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, Model model){

        try{

            String uploadError = null;
            User user = userService.getUser(authentication.getName());
            File myFile = new File();

            myFile.setFiledata(file.getBytes());
            myFile.setFileSize(String.valueOf(file.getSize()));
            myFile.setContentType(file.getContentType());
            myFile.setFilename(file.getOriginalFilename());
            myFile.setUserid(user.getUserId());


            if (!fileService.isFileAvailable(myFile.getFilename())) {
                uploadError = "The file already exists.";
            }


            if (uploadError == null) {
                int rowsAdded = fileService.createFile(myFile);
                if (rowsAdded < 0) {
                    uploadError = "There was an error uploading the file. Please try again.";
                }
            }

            if (uploadError == null) {
                model.addAttribute("uploadSuccess", true);
            } else {
                model.addAttribute("uploadError", uploadError);
            }



        }catch (Exception e){
            model.addAttribute("uploadError", e.getMessage());
        }
        homeView(model);
        return "home";
    }

    @GetMapping("files/{fileId}")
    public  String deleteFile(@PathVariable(name = "fileId") Integer fileId, Model model){
        fileService.deleteFile(fileId);
        homeView(model);
        return "home";
    }

}
