package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.apache.commons.validator.UrlValidator;
import org.hibernate.validator.internal.constraintvalidators.hv.URLValidator;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public boolean isExist(Integer credential){
        return getCredential(credential) != null;
    }

    public boolean isUsernameExisted(String username){
        return credentialMapper.getCredentialByUsername(username) != null;
    }

    public boolean isValidUrl(String url){
        UrlValidator urlValidator = new UrlValidator();
        return true;
    }

    public Credential getCredential(Integer credential){
        return credentialMapper.getCredential(credential);
    }
    public int createCredential(Credential credential) {
        return credentialMapper.insertCredential(new Credential(null,
                credential.getUrl(),
                credential.getUsername(),
                credential.getKey(),
                credential.getPassword(),
                credential.getUserid()));
    }

    public List<Credential> getAllCredential(Integer userId ) {
        return credentialMapper.getAllCredentials(userId);
    }

    public void deleteCredential(Integer credential) {
        credentialMapper.deleteCredential(credential);
    }

    public void updateCredential(Credential credential) {
        credentialMapper.updateCredential(credential.getUserid(), credential.getUrl(), credential.getUsername(), credential.getKey(), credential.getPassword());
    }


}
