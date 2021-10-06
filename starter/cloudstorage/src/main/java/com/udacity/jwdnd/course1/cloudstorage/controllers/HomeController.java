package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.File;
import com.udacity.jwdnd.course1.cloudstorage.models.Note;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("/home")
public class HomeController {


    private final FileService fileService;
    private final UserService userService;
    private final NoteService noteService;
    private final CredentialService credentialService;
    private final EncryptionService encryptionService;

    public HomeController(FileService fileService, UserService userService, NoteService noteService, CredentialService credentialService, EncryptionService encryptionService) {
        this.fileService = fileService;
        this.userService = userService;
        this.noteService = noteService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
    }

    @GetMapping
    public String homeView(Model model) {
        model.addAttribute("files", this.fileService.getFiles());
        model.addAttribute("notes", this.noteService.getAllNotes());
        model.addAttribute("credentials", this.credentialService.getAllCredential());
        return "home";
    }

    @PostMapping("files")
    public String uploadFile(Authentication authentication, @RequestParam("fileUpload") MultipartFile file, Model model) {

        try {

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


        } catch (Exception e) {
            model.addAttribute("uploadError", e.getMessage());
        }

        return homeView(model);
    }

    @GetMapping(
            value = "/get-files/{fileName}",
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
    public String deleteFile(@PathVariable(name = "fileId") Integer fileId, Model model) {
        fileService.deleteFile(fileId);
        return homeView(model);
    }

    @PostMapping("notes")
    public String submitNote(Authentication authentication, @ModelAttribute Note note, Model model) {
        User user = userService.getUser(authentication.getName());
        note.setUserid(user.getUserId());

        if (noteService.isExist(note.getNoteId())) {
            noteService.updateNote(note);
        } else {
            noteService.createNote(note);
        }

        return homeView(model);
    }

    @GetMapping("notes/{noteId}")
    public String deleteNote(@PathVariable(name = "noteId") Integer noteId, Model model) {
        noteService.deleteNote(noteId);
        return homeView(model);
    }

    @PostMapping("credentials")
    public String submitCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {

        User user = userService.getUser(authentication.getName());


        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setUserid(user.getUserId());
        credential.setKey(encodedKey);
        credential.setPassword(encryptedPassword);


        if (credentialService.isExist(credential.getCredentialId())) {
            credentialService.updateCredential(credential);
        }else {
            credentialService.createCredential(credential);
        }


        return homeView(model);
    }

    @GetMapping("credentials/{credentialId}")
    public String deleteCredential(@PathVariable(name = "credentialId") Integer credentialId, Model model) {
        credentialService.deleteCredential(credentialId);
        return homeView(model);
    }

}
