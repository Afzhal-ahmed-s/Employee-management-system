package com.ems.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ems.exception.FileException;
import com.ems.serviceImplimentation.FileService;

@Controller
@RequestMapping("/files")
public class FileController {

    private final FileService fileService;
    
    @Autowired
    public Environment environment;

    @Autowired
    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @GetMapping(value = "/download/{employeeID}/{documentType}", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> downloadFiles(@PathVariable String employeeID, @PathVariable String documentType) throws IOException, FileException {

    	String folderPath = environment.getProperty("filePath") + employeeID +"/"+ documentType;
    	//String folderPath = "/Users/afzhalahmed/Documents/GitHub/Employee-management-system/Files/" + employeeID +"/"+ documentType;

        String zipFileName = documentType;
        //String zipFileName = folderName.substring(folderName.lastIndexOf(File.separator) + 1);

    	byte[] zipBytes = fileService.zipDirectory(folderPath);
        return ResponseEntity
                .ok()
                .header("Content-Disposition", "attachment; filename="+zipFileName+".zip")
                .body(zipBytes);
    }
}

