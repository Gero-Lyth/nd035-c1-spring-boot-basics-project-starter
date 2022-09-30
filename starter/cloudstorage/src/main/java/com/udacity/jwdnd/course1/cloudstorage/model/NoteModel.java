package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteModel {
    private Integer noteid;
    private String noteTitle;
    private String noteDescription;
    private Integer userid;

    public NoteModel(Integer noteid, String noteTitle, String noteDescription, Integer userid) {
        this.noteid = noteid;
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
        this.userid = userid;
    }

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }

    public String getNoteTitle() {
        return noteTitle;
    }

    public void setNoteTitle(String noteTitle) {
        this.noteTitle = noteTitle;
    }

    public String getNoteDescription() {
        return noteDescription;
    }

    public void setNoteDescription(String noteDescription) {
        this.noteDescription = noteDescription;
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}
