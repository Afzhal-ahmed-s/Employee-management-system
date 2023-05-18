package com.ems.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import com.ems.enums.DocumentType;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Document {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int documentId;
    private String number;
    private DocumentType type;
    private LocalDate dateOfIssue;
    private LocalDate dateOfExpiry;
    
    @Column(name = "file")
    @ElementCollection(targetClass = String.class)
    private List<String> file;
	
}
