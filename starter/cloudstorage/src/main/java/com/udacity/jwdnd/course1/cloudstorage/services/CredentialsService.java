package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialsMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModel;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialsService {
    private CredentialsMapper credentialsMapper;
    private UserMapper userMapper;
    private EncryptionService encryptionService;
    public CredentialsService(CredentialsMapper credentialsMapper, UserMapper userMapper, EncryptionService encryptionService){
        this.credentialsMapper = credentialsMapper;
        this.userMapper = userMapper;
        this.encryptionService = encryptionService;
    }
    public List<CredentialModel> getCredentials(String username){
        List<CredentialModel> creds = credentialsMapper.getUserCredentials(userMapper.getUserID(username));
        creds.forEach((CredentialModel model) -> {
            System.out.println(model.getCredentialid());
            model.setPassword(encryptionService.decryptValue(model.getPassword(),model.getKey()));
        });
        return creds;
    }

    public int saveCredentials(Authentication authentication, CredentialForm credentialForm){
        String key = "";
        try {
            SecureRandom sr = SecureRandom.getInstanceStrong();
            byte[] salt = new byte[128];
            sr.nextBytes(salt);
            key= Base64.getEncoder().encodeToString(salt);
            System.out.println(key.getBytes().length);
        } catch (Exception e){

        }
        String pw = encryptionService.encryptValue(credentialForm.getPassword(), key);
        return credentialsMapper.insert(new CredentialModel(null,credentialForm.getUrl(), credentialForm.getUsername(), key, pw, userMapper.getUserID(authentication.getName())));
    }

    public int deleteCredential(Integer credentialId, Authentication authentication) {
        CredentialModel credentialModel = credentialsMapper.getCredential(credentialId);
        if(credentialModel == null)
            return -1;
        if(userMapper.getUserID(authentication.getName()) != credentialModel.getUserid())
            return -2;
        credentialsMapper.delete(credentialId);
        return 0;
    }

    public int updateCredentials(CredentialForm credentialForm, Authentication authentication) {
        CredentialModel credentialModel = credentialsMapper.getCredential(credentialForm.getCredentialId());
        if (credentialModel == null)
            return -2;
        if(userMapper.getUserID(authentication.getName()) != credentialModel.getUserid()){
            return -1;
        }
        String pw = encryptionService.encryptValue(credentialForm.getPassword(), credentialModel.getKey());
        credentialModel.setPassword(pw);
        credentialsMapper.update(credentialModel);
        return 0;
    }
}
