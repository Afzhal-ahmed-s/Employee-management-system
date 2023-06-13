package com.ems.controller;

import java.io.File;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ems.exception.FileException;
import com.ems.serviceImplimentation.FileService;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> downloadFiles(@RequestParam String folderName) throws IOException, FileException {

    	String folderPath = "/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/" + folderName;
        String zipFileName = folderName.substring(folderName.lastIndexOf(File.separator) + 1);

    	byte[] zipBytes = fileService.zipDirectory(folderPath);
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename="+zipFileName+".zip")
                .body(zipBytes);
    }
}

