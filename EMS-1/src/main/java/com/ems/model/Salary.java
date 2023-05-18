package com.ems.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Salary {

	@Id
	private int salaryId;
	
//    private int employeeId;
    
    @OneToOne(cascade = CascadeType.ALL)
    private Employee employee;
    
    private String bankAccountName;
    private String bankAccountNumber;
    private String bankAccountIFSC;
    private String transactionId;
    private LocalDate creditDate;
    private int salaryYear;
    private String salaryMonth;
    private double salaryTotal;
    private double salaryAmount;
    private String salaryCurrency;
    private double tdsAmount;
    private double deductionAmount;
   
    @OneToOne(cascade = CascadeType.ALL)
    private Document salarySlip;
    
    private String notes;
	
}
