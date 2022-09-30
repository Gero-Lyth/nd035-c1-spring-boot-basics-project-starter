package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.FileModel;
import com.udacity.jwdnd.course1.cloudstorage.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.ByteArrayInputStream;

@Controller
public class FileUploadController {


    private final StorageService storageService;

    @Autowired
    public FileUploadController(StorageService storageService) {
        this.storageService = storageService;
    }





    @GetMapping("/download/{fileId}")
    @ResponseBody
    public ResponseEntity<InputStreamResource> serveFile(@PathVariable Integer fileId, Authentication authentication) {
        FileModel fileModel = storageService.getFile(fileId, authentication);
        if(fileModel.getFileId() == -1){
            return ResponseEntity.badRequest().body(null);
        }
        if(fileModel.getFileId() == -2){
            return ResponseEntity.notFound().build();
        }

        InputStreamResource resource = new InputStreamResource(new ByteArrayInputStream(fileModel.getFiledata()));

        return ResponseEntity.ok()
                // Content-Disposition
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileModel.getFileName())
                // Content-Type
                .contentType(MediaType.parseMediaType(fileModel.getContenttype()))
                // Content-Length
                .contentLength(Long.parseLong(fileModel.getFilesize())) //
                .body(resource);

    }
    @GetMapping("/delete/file/{fileId}")
    public String handleFileDelete(@PathVariable Integer fileId,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {

        int ret = storageService.deleteFile(fileId, authentication);
        if(ret == 0){
            redirectAttributes.addFlashAttribute("message",
                    "You successfully deleted file!");
            redirectAttributes.addFlashAttribute("success",true);
        } else if (ret == -1) {
            redirectAttributes.addFlashAttribute("message",
                    "File ID not found.");
            redirectAttributes.addFlashAttribute("error",true);
        } else if (ret == -2) {
            redirectAttributes.addFlashAttribute("message",
                    "That's not your file, bucko.");
            redirectAttributes.addFlashAttribute("error",true);
        }
        return "redirect:/result";
    }
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("fileUpload") MultipartFile file,
                                   Authentication authentication,
                                   RedirectAttributes redirectAttributes) {
        System.out.println(file.getOriginalFilename());

        storageService.store(file, authentication.getName());
        redirectAttributes.addFlashAttribute("message",
                "You successfully uploaded " + file.getOriginalFilename() + "!");
        redirectAttributes.addFlashAttribute("success",true);
        return "redirect:/result";
    }
}
