package com.ems.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.ems.enums.DocumentType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int documentId;
    private String number;
    private DocumentType type;
    private LocalDate dateOfIssue;
    private LocalDate dateOfExpiry;
    
    private List<String> file;
	
}
