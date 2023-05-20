package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ems.exception.EmployeeException;
import com.ems.exception.SalaryException;
import com.ems.model.Salary;
import com.ems.model.SalaryDTO;

@Service
public interface SalaryService {

	public Salary addSalary(Salary salary)throws SalaryException, EmployeeException;
	public Salary getSalaryById(Integer salaryId)throws SalaryException;
	public List<Salary> getSalaryByEmployeeId(Integer employeeId)throws EmployeeException;
	public Salary getSalaryBySalaryIdAndEmployeeId(Integer employeeId, Integer salaryId) throws SalaryException;
	public SalaryDTO deleteSalaryBySalaryId(Integer salaryId) throws SalaryException;
}
