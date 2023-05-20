package com.ems.serviceImplimentation;

import org.springframework.stereotype.Service;

import com.ems.enums.Department;
import com.ems.enums.DocumentType;
import com.ems.enums.Gender;
import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.model.Document;
import com.ems.model.DocumentInputFormat;
import com.ems.model.Employee;
import com.ems.model.EmployeeInputFormat;
import com.ems.model.User;
import com.ems.service.EnumConversionService;

@Service
public class EnumConversionServiceImplementation implements EnumConversionService{

	@Override
	public Employee employeeInputFormatToemployeeWithGenderEnumsConvertor(EmployeeInputFormat employeeInputFormat,
			Employee employee) throws EmployeeException {

//		String gender = employeeInputFormat.getGender().toString().toUpperCase();
//		if(gender.compareToIgnoreCase("MALE") == 0)employee.setGender(Gender.MALE);
//		else if(gender.compareToIgnoreCase("FEMALE") == 0)employee.setGender(Gender.FEMALE);
//		else throw new EmployeeException("Incorrect gender input");
		
		System.out.println("Enums gender converted to- "+ employee.getGender());
		
		return employee;
	}

	@Override
	public Employee employeeInputFormatToemployeeWithDepartmentTypeEnumsConvertor(
			EmployeeInputFormat employeeInputFormat, Employee employee) throws EmployeeException {

//		String department = employeeInputFormat.getDepartment().toString().toUpperCase();
//		if(department.compareToIgnoreCase("Development") == 0)employee.setDepartment(Department.Development);
//		else if(department.compareToIgnoreCase("QA") == 0)employee.setDepartment(Department.QA);
//		else if(department.compareToIgnoreCase("Admin") == 0)employee.setDepartment(Department.Admin);
//		else throw new EmployeeException("Incorrect department input");
		
		System.out.println("Enums department type converted to- "+ employee.getDepartment());
		
		return employee;
	}

	@Override
	public User userRoleEnumConvertor(User user) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Document documentTypeEnumConvertor(DocumentInputFormat documentInputFormat, Document document) throws DocumentException {

		String documentType = documentInputFormat.getType();
		
		for(DocumentType e : DocumentType.values()) {
			if(e.toString().compareToIgnoreCase(documentType) == 0) {
				document.setType(e.toString());
				return document;
			}
		}
		throw new DocumentException("Please provide proper name for document type.");
	}

	
	
}
