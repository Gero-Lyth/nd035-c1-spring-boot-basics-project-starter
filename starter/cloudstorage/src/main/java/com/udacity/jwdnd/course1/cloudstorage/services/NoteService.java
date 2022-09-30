package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialModel;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteModel;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {
    private NoteMapper noteMapper;
    private UserMapper userMapper;

    public NoteService(NoteMapper noteMapper, UserMapper userMapper){
        this.noteMapper = noteMapper;
        this.userMapper = userMapper;
    }

    public List<NoteModel> getNotes(String username){
        User user = userMapper.getUser(username);
        return noteMapper.getUserNotes(user.getUserId());
    }

    public int saveNote(Authentication authentication, NoteForm noteForm){
        return noteMapper.insertNote(new NoteModel(null,noteForm.getNoteTitle(),noteForm.getNoteDescription(),userMapper.getUserID(authentication.getName())));
    }
    public int updateNote(Authentication authentication, NoteForm noteForm){
        NoteModel noteModel = noteMapper.getNote(noteForm.getNoteid());
        if (noteModel == null)
            return -1;
        if(userMapper.getUserID(authentication.getName()) != noteMapper.getNote(noteForm.getNoteid()).getUserid()){
            return -2;
        }
        noteMapper.updateNote(new NoteModel(noteForm.getNoteid(),noteForm.getNoteTitle(),noteForm.getNoteDescription(),userMapper.getUserID(authentication.getName())));
        return 0;
    }

    public int deleteNote(Integer noteId, Authentication authentication) {

        NoteModel noteModel = noteMapper.getNote(noteId);
        if (noteModel == null)
            return -2;
        if(userMapper.getUserID(authentication.getName()) != noteModel.getUserid()){
            return -1;
        }
        noteMapper.deleteNote(noteId);
        return 0;
    }
}
