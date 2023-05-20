package com.ems.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Leaves {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
	 	private Integer leaveId;
		
	    private Integer employeeId;
	    
//	    @OneToOne(cascade = CascadeType.ALL)
//	    private Employee employee;
	    
	    private String leaveType;
	    private LocalDate leaveDate;
	    private LocalDate leaveFrom;
	    private LocalDate leaveTo;
	    private String leaveDescription;
	
}
