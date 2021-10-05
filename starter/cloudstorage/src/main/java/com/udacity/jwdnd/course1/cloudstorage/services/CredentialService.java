package com.udacity.jwdnd.course1.cloudstorage.services;


import com.udacity.jwdnd.course1.cloudstorage.mappers.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.models.Credential;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CredentialService {

    private final CredentialMapper credentialMapper;

    public CredentialService(CredentialMapper credentialMapper) {
        this.credentialMapper = credentialMapper;
    }

    public int createCredential(Credential credential) {
        return credentialMapper.insertCredential(new Credential(null,
                credential.getUrl(),
                credential.getUsername(),
                credential.getKey(),
                credential.getPassword(),
                credential.getUserid()));
    }

    public List<Credential> getAllCredential( ) {
        return credentialMapper.getAllCredentials();
    }


}
