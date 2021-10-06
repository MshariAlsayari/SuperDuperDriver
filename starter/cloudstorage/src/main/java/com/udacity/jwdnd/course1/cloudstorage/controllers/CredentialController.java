package com.udacity.jwdnd.course1.cloudstorage.controllers;


import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import com.udacity.jwdnd.course1.cloudstorage.models.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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


        model.addAttribute("credentials", this.credentialService.getAllCredential());
        return "home";
    }

    @GetMapping("/{credentialId}")
    public String deleteCredential(@PathVariable(name = "credentialId") Integer credentialId, Model model) {
        credentialService.deleteCredential(credentialId);
        model.addAttribute("credentials", this.credentialService.getAllCredential());
        return "home";
    }
}
