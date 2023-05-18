package com.ems.model;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import com.ems.enums.Department;
import com.ems.enums.Gender;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.bytebuddy.utility.dispatcher.JavaDispatcher.Defaults;

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
	    
	    @Email(message = "Invalid email")
	    private String emailAddress;
	    
	    @OneToMany(cascade = CascadeType.ALL)
	    private List<Document> documents;
	    
	    private Department department;
	
	
}
