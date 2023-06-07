package com.ems.model;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.validation.constraints.Email;

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
	    private Integer computationalEmployeeId;
		
		@Column(unique = true)
		private String employeeID;
	    private String firstName;
	    private String lastName;
	    
	    
		@Column(unique = true)
	    private String phoneNumber;
	    private LocalDate dateOfBirth;
	    private LocalDate dateOfJoining;
	    private LocalDate dateOfLeaving;
	    
	    private String gender;
	    
	    @Email(message = "Invalid email or such e-mail already exists in records.")		
	    @Column(unique = true)
	    private String emailAddress;
	    
	    @OneToMany(cascade = CascadeType.ALL)
	    @JoinColumn(name = "employee_id")
	    private List<Document> documents;
	    
	    private String department;
	
	
}
