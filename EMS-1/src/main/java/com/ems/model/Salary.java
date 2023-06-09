package com.ems.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Salary {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer salaryId;
	
    private Integer employeeId;
    
//    @OneToOne(cascade = CascadeType.ALL)
//    private Employee employee;
    
    private String bankAccountName;
    private String bankAccountNumber;
    private String bankAccountIFSC;
    private String transactionId;
    private LocalDate creditDate;
    private Integer salaryYear;
    private String salaryMonth;
    private Double salaryTotal;
    private Double salaryAmount;
    private String salaryCurrency;
    private Double tdsAmount;
    private Double deductionAmount;
   
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Document salarySlip;
    
    private String notes;
	
}
