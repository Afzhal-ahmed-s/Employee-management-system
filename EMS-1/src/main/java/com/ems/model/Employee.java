package com.ems.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.ems.enums.Department;
import com.ems.enums.Gender;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Employee {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	    private int employeeId;
	    private String firstName;
	    private String lastName;
	    private String phoneNumber;
	    private LocalDate dateOfBirth;
	    private LocalDate dateOfJoining;
	    private LocalDate dateOfLeaving;
	    private Gender gender;
	    
	    
	    private String emailAddress;
	    
	    @OneToMany(cascade = CascadeType.ALL)
	    private List<Document> documents;
	    
	    private Department department;
	
	
}
