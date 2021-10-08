package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.STATUS;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Base64;

@Controller
@RequestMapping("credentials")
public class CredentialController {

    private final CredentialService credentialService;
    private final UserService userService;
    private final EncryptionService encryptionService;

    public CredentialController(CredentialService credentialService, UserService userService, EncryptionService encryptionService) {
        this.credentialService = credentialService;
        this.userService = userService;
        this.encryptionService = encryptionService;
    }

    @PostMapping()
    public String submitCredential(Authentication authentication, @ModelAttribute Credential credential, Model model) {

        User user = userService.getUser(authentication.getName());

        STATUS status;
        String message;
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        String encodedKey = Base64.getEncoder().encodeToString(key);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedKey);
        credential.setUserid(user.getUserId());

        if (!credentialService.isValidUrl(credential.getUrl())) {
            status = STATUS.error;
            message = " The credential url is not valid";
        }else {


            if (credentialService.isExist(credential.getCredentialId())) {
                String password = credential.getPassword();
                Credential existedCredential = credentialService.getCredential(credential.getUserid());
                if (existedCredential !=null ) {
                    String existedPassword = encryptionService.decryptValue(existedCredential.getPassword(), existedCredential.getKey());
                    // need to update
                    if (!password.equals(existedPassword)) {
                        credential.setKey(encodedKey);
                        credential.setPassword(encryptedPassword);
                    } else {
                        credential.setKey(existedCredential.getKey());
                        credential.setPassword(existedCredential.getPassword());
                    }
                }
                credentialService.updateCredential(credential);
                status = STATUS.success;
                message = " The credential updated successfully";
            } else {
                if (credentialService.isUsernameExisted(credential.getUsername())) {
                    status = STATUS.error;
                    message = "User already available";
                } else {
                    credential.setKey(encodedKey);
                    credential.setPassword(encryptedPassword);
                    status = STATUS.success;
                    message = " The credential is created";
                    credentialService.createCredential(credential);
                }
            }
        }

        model.addAttribute("credentials", this.credentialService.getAllCredential(user.getUserId()));
      //  model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("message", message);
        model.addAttribute("result", status.name());
        return "result";
    }

    @GetMapping("/{credentialId}")
    public String deleteCredential(Authentication authentication,@PathVariable(name = "credentialId") Integer credentialId, Model model) {
        User user = userService.getUser(authentication.getName());
        credentialService.deleteCredential(credentialId);
        model.addAttribute("credentials", this.credentialService.getAllCredential(user.getUserId()));
        model.addAttribute("encryptionService", encryptionService);
        model.addAttribute("message", "The credential is deleted");
        model.addAttribute("result", STATUS.success.name());
        return "result";
    }
}
