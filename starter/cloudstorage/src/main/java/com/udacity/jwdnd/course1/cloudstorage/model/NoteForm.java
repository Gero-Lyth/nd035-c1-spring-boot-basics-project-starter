package com.udacity.jwdnd.course1.cloudstorage.model;

public class NoteForm {
    private Integer noteid;
    private String noteTitle;
    private String noteDescription;

    public NoteForm(Integer noteid, String noteTitle, String noteDescription, Integer userid) {
        this.noteTitle = noteTitle;
        this.noteDescription = noteDescription;
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

    public Integer getNoteid() {
        return noteid;
    }

    public void setNoteid(Integer noteid) {
        this.noteid = noteid;
    }
}
