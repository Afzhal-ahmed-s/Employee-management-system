package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.model.DateRange;
import com.ems.model.Document;
import com.ems.model.Employee;
import com.ems.model.Leaves;

@Service
public interface EmployeeService {

	public Employee addEmployee(Employee employee)throws EmployeeException;
	
	public Employee getEmployeeById(Integer employeeId)throws EmployeeException;
	
	public Employee updateEmployeeById(Employee employee, Integer employeeId)throws EmployeeException , DocumentException;
	
	public List<Document> getAlldocumentsById(Integer employeeId) throws DocumentException, EmployeeException;
	
	public List<Leaves> getAllLeavesByEmployeeIdWithinDateRange(Integer employeeID, DateRange dateRange);
}
