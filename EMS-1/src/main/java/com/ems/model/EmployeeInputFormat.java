package com.ems.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import lombok.Data;

//@Entity
@Data
public class EmployeeInputFormat {


	    private String firstName;
	    private String lastName;
	    private String phoneNumber;
	    private LocalDate dateOfBirth;
	    private LocalDate dateOfJoining;
	    private LocalDate dateOfLeaving;
	    private String gender;
	    
	    private String emailAddress;
	    
	    @OneToMany(cascade = CascadeType.ALL)
	    private List<Document> documents;
	    
	    private String department;
	
	
}
