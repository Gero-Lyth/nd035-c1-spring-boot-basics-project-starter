package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.model.NoteForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/home")
public class HomeController {
    private FileService fileService;
    private CredentialsService credentialsService;
    private NoteService noteService;

    public HomeController(FileService fileService, CredentialsService credentialsService, NoteService noteService) {
        this.fileService = fileService;
        this.credentialsService = credentialsService;
        this.noteService = noteService;
    }
    @GetMapping
    public String getHome(Authentication authentication,
                          Model model,
                          NoteForm noteForm,
                          CredentialForm credentialForm){
        model.addAttribute("files",fileService.getFiles(authentication.getName()));
        model.addAttribute("notes",noteService.getNotes(authentication.getName()));
        model.addAttribute("credentials",credentialsService.getCredentials(authentication.getName()));
        return "home";
    }
}
