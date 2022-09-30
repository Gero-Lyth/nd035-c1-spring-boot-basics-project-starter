package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class NotesController {
    private NoteService noteService;
    public NotesController(NoteService noteService){
        this.noteService = noteService;
    }

    @PostMapping("/note")
    public String submitNote(Authentication authentication, NoteForm noteForm, RedirectAttributes redirectAttributes){
        if(noteForm.getNoteid() == null){
            noteService.saveNote(authentication,noteForm);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully created the note " + noteForm.getNoteTitle() + "!");
            redirectAttributes.addFlashAttribute("success",true);
        } else {
            int ret = noteService.updateNote(authentication,noteForm);
            if(ret == -2){
                redirectAttributes.addFlashAttribute("message",
                        "That wasn't your note, bucko. ");
                redirectAttributes.addFlashAttribute("error",true);
            } else if (ret == -1) {
                redirectAttributes.addFlashAttribute("message",
                        "Note with ID " + noteForm.getNoteid() + " doesn't exist");
                redirectAttributes.addFlashAttribute("error",true);

            } else {
            redirectAttributes.addFlashAttribute("message",
                    "You successfully updated the note " + noteForm.getNoteTitle() + "!");
            redirectAttributes.addFlashAttribute("success",true);
            }
        }

        return "redirect:/result";
    }
    @PostMapping("/delete/file/{noteId}")
    public String handleFileDelete(@PathVariable Integer noteId,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {

        int ret = noteService.deleteNote(noteId, authentication);
        if(ret == 0){
            redirectAttributes.addFlashAttribute("message",
                    "You successfully deleted the note!");
            redirectAttributes.addFlashAttribute("success",true);
        } else if (ret == -1) {
            redirectAttributes.addFlashAttribute("message",
                    "Note ID not found.");
            redirectAttributes.addFlashAttribute("error",true);
        } else if (ret == -2) {
            redirectAttributes.addFlashAttribute("message",
                    "That's not your note, bucko.");
            redirectAttributes.addFlashAttribute("error",true);
        }
        return "redirect:/result";
    }
}