package com.udacity.jwdnd.course1.cloudstorage.model;

public class CredentialForm {

    private Integer credentialId;
    private String url;
    private String username;
    private String password;

    public CredentialForm(String url, String username, String key, Integer credentialId, String password) {
        this.url = url;
        this.username = username;
        this.credentialId = credentialId;
        this.password = password;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getCredentialId() {
        return credentialId;
    }

    public void setCredentialId(Integer credentialId) {
        this.credentialId = credentialId;
    }
}
