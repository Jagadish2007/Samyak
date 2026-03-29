package com.example.lms.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

@RestController
@RequestMapping("/api/files")
@CrossOrigin
public class FileController {

    private final String baseDir = System.getProperty("user.dir") + "/uploads/";

    // ✅ Upload file to course folder
    @PostMapping("/upload/{courseName}")
    public String uploadFile(
            @PathVariable String courseName,
            @RequestParam("file") MultipartFile file) {

        try {
            // Create course folder
            String folderPath = baseDir + courseName + "/";
            File folder = new File(folderPath);

            if (!folder.exists())
                folder.mkdirs();

            // Save file
            File destination = new File(folderPath + file.getOriginalFilename());
            file.transferTo(destination);

            return "Uploaded to " + courseName;

        } catch (IOException e) {
            e.printStackTrace();
            return "Upload failed: " + e.getMessage();
        }
    }

    // ✅ List files of a course
    @GetMapping("/list/{courseName}")
    public String[] listFiles(@PathVariable String courseName) {

        File folder = new File(baseDir + courseName + "/");

        if (!folder.exists())
            return new String[0];

        return folder.list();
    }

    @GetMapping("/download/{courseName}/{fileName}")
    public org.springframework.http.ResponseEntity<org.springframework.core.io.Resource> download(
            @PathVariable String courseName,
            @PathVariable String fileName) throws IOException {

        String baseDir = System.getProperty("user.dir") + "/uploads/";
        File file = new File(baseDir + courseName + "/" + fileName);

        org.springframework.core.io.Resource resource = new org.springframework.core.io.UrlResource(file.toURI());

        return org.springframework.http.ResponseEntity.ok()
                // ✅ FORCE DOWNLOAD
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getName() + "\"")

                // ✅ Set correct type
                .header(org.springframework.http.HttpHeaders.CONTENT_TYPE,
                        "application/octet-stream")

                .body(resource);
    }
}