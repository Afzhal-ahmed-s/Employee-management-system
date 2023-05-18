package com.ems.model;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import jakarta.persistence.OneToOne;
import lombok.Data;

@Entity
@Data
public class Leaves {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	 	private int leaveId;
		
//	    private int employeeId;
	    
	    @OneToOne(cascade = CascadeType.ALL)
	    private Employee employee;
	    
	    private String leaveType;
	    private LocalDate leaveDate;
	    private LocalDate leaveFrom;
	    private LocalDate leaveTo;
	    private String leaveDescription;
	
}
