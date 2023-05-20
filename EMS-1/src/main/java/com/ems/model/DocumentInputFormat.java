package com.ems.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DocumentInputFormat {

    private Integer number;
    private String type;
    private LocalDate dateOfIssue;
    private LocalDate dateOfExpiry;
    
    private List<String> file;
	
}
