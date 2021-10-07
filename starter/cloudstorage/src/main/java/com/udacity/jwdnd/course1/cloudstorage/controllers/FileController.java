package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("files")
public class FileController {

    private final FileService fileService;
    private final UserService userService;

    public FileController(FileService fileService, UserService userService) {
        this.fileService = fileService;
        this.userService = userService;
    }

    @PostMapping()
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, Model model) {
        User user = userService.getUser(authentication.getName());
        try {

            String uploadError = null;

            File myFile = new File();

            myFile.setFiledata(file.getBytes());
            myFile.setFileSize(String.valueOf(file.getSize()));
            myFile.setContentType(file.getContentType());
            myFile.setFilename(file.getOriginalFilename());
            myFile.setUserid(user.getUserId());

            // check if no file uploaded
            if (myFile.getFiledata().length == 0){
                uploadError = "No file to upload.";
            }


            // check if file is existed
            if (!fileService.isFileAvailable(myFile.getFilename())) {
                uploadError = "The file already exists.";
            }

            // check if the file is large
            if (fileService.isFileLarge(myFile)){
                uploadError = "The file is larger than 5MB.";
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


        } catch (Exception e) {
            model.addAttribute("uploadError", e.getMessage());
        }

        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        return "home";
    }

    @GetMapping(
            value = "get-files/{fileName}",
            produces = {MediaType.APPLICATION_PDF_VALUE,
                    MediaType.APPLICATION_JSON_VALUE,
                    MediaType.APPLICATION_CBOR_VALUE,
                    MediaType.APPLICATION_PROBLEM_XML_VALUE}
    )
    public @ResponseBody
    byte[] getFile(@PathVariable(name = "fileName") String fileName) {
        return fileService.getFile(fileName).getFiledata();
    }

    @GetMapping("delete-files/{fileId}")
    public String deleteFile(Authentication authentication, @PathVariable(name = "fileId") Integer fileId, Model model) {
        User user = userService.getUser(authentication.getName());
        fileService.deleteFile(fileId);
        model.addAttribute("files", this.fileService.getFiles(user.getUserId()));
        return "home";
    }
}
