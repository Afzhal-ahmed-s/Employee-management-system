package com.ems.service;

import org.springframework.stereotype.Service;

import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.model.Document;
import com.ems.model.DocumentInputFormat;
import com.ems.model.Employee;
import com.ems.model.EmployeeInputFormat;
import com.ems.model.User;

@Service
public interface EnumConversionService {
	
	
	public Employee employeeInputFormatToemployeeWithGenderEnumsConvertor(EmployeeInputFormat EmployeeInputFormat, Employee employee) throws EmployeeException;
	
	public Employee employeeInputFormatToemployeeWithDepartmentTypeEnumsConvertor(EmployeeInputFormat EmployeeInputFormat, Employee employee) throws EmployeeException;

	public User userRoleEnumConvertor(User user);
	
	public Document documentTypeEnumConvertor(DocumentInputFormat documentInputFormat, Document document) throws DocumentException;


}
