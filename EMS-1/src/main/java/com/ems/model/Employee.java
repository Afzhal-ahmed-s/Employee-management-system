package com.ems.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.ems.enums.Department;
import com.ems.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;

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
	    private Integer employeeId;
	    private String firstName;
	    private String lastName;
	    private String phoneNumber;
	    private LocalDate dateOfBirth;
	    private LocalDate dateOfJoining;
	    private LocalDate dateOfLeaving;
	    
	    private String gender;
	    
	    private String emailAddress;
	    
	    @OneToMany(cascade = CascadeType.ALL)
	    @JoinColumn(name = "employee_id")
	    private List<Document> documents;
	    
	    private String department;
	
	
}
