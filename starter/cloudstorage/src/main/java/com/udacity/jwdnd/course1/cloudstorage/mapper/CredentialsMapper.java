package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModel;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM CREDENTIALS WHERE USERID = #{userid}")
    List<CredentialModel> getUserCredentials(int userid);

    @Select("SELECT * FROM CREDENTIALS WHERE CREDENTIALID = #{credId}")
    CredentialModel getCredential(int credId);
    @Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid) VALUES(#{url}, #{username}, #{key}, #{password}, #{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(CredentialModel credentialModel);

    @Delete("DELETE FROM CREDENTIALS WHERE CREDENTIALID = #{credId}")
    void delete(int credId);

    @Update("UPDATE CREDENTIALS SET URL = #{url} , USERNAME = #{username}, KEY = #{key}, PASSWORD = #{password} WHERE CREDENTIALID = #{credentialid}")
    void update(CredentialModel credentialModel);
}
