package com.ems.model;

import java.time.LocalDate;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SalaryDTO {


	private Integer salaryId;
	
    private Integer employeeId;
    
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
   
    private Document salarySlip;
    
    private String notes;
	
}
