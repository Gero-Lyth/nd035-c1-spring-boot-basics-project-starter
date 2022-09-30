package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.CredentialForm;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialsService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class CredentialsController {
    private CredentialsService credentialsService;

    public CredentialsController(CredentialsService credentialsService) {
        this.credentialsService = credentialsService;
    }

    @PostMapping(("/credential"))
    String postCredential(Authentication authentication, CredentialForm credentialForm, RedirectAttributes redirectAttributes){
        if(credentialForm.getUrl().length() > 100){
            redirectAttributes.addFlashAttribute("message",
                    "URL, max length is 100, your submission was " + credentialForm.getUrl().length() + ".");
            redirectAttributes.addFlashAttribute("error",true);
            return "redirect:/result";
        }
        if(credentialForm.getUsername().length() > 30){
            redirectAttributes.addFlashAttribute("message",
                    "Username too long, max length is 30, your submission was " + credentialForm.getUsername().length() + ".");
            redirectAttributes.addFlashAttribute("error",true);
            return "redirect:/result";
        }
        if(credentialForm.getCredentialId() == null){
        credentialsService.saveCredentials(authentication,credentialForm);
        redirectAttributes.addFlashAttribute("message",
                "You successfully set credentials for " + credentialForm.getUrl() + "!");
        redirectAttributes.addFlashAttribute("success",true);
        } else {
            int ret = credentialsService.updateCredentials(credentialForm,authentication);
            if(ret == 0){
                credentialsService.saveCredentials(authentication,credentialForm);
                redirectAttributes.addFlashAttribute("message",
                        "You successfully changed credentials for " + credentialForm.getUrl() + "!");
                redirectAttributes.addFlashAttribute("success",true);
            }
        }
        return "redirect:/result";
    }
    @GetMapping("/delete/credential/{credentialId}")
    public String handleFileDelete(@PathVariable Integer credentialId,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {

        int ret = credentialsService.deleteCredential(credentialId, authentication);
        if(ret == 0){
            redirectAttributes.addFlashAttribute("message",
                    "You successfully deleted the credentials!");
            redirectAttributes.addFlashAttribute("success",true);
        } else if (ret == -1) {
            redirectAttributes.addFlashAttribute("message",
                    "Credential ID ID not found.");
            redirectAttributes.addFlashAttribute("error",true);
        } else if (ret == -2) {
            redirectAttributes.addFlashAttribute("message",
                    "That's not your data, bucko.");
            redirectAttributes.addFlashAttribute("error",true);
        }
        return "redirect:/result";
    }
}
