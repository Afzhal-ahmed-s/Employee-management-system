package com.ems.serviceImplimentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.enums.Department;
import com.ems.enums.Gender;
import com.ems.exception.EmployeeException;
import com.ems.model.Employee;
import com.ems.model.EmployeeInputFormat;
import com.ems.repository.EmployeeRepository;
import com.ems.service.EmployeeService;

@Service
public class EmployeeServiceImplimentation implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Override
	public Employee addEmployee(EmployeeInputFormat employee) throws EmployeeException {

		Employee insertionEmployee = new Employee(0, employee.getFirstName(),
				employee.getLastName(), employee.getPhoneNumber(), employee.getDateOfBirth(),
				employee.getDateOfJoining(), employee.getDateOfLeaving(),
				null, employee.getEmailAddress(), employee.getDocuments(), null);


		String gender = employee.getGender().toString().toUpperCase();
		if(gender.compareToIgnoreCase("MALE") == 0)insertionEmployee.setGender(Gender.MALE);
		else if(gender.compareToIgnoreCase("FEMALE") == 0)insertionEmployee.setGender(Gender.FEMALE);
		else throw new EmployeeException("Incorrect gender input");
		
		String department = employee.getDepartment().toString().toUpperCase();
		if(department.compareToIgnoreCase("Development") == 0)insertionEmployee.setDepartment(Department.Development);
		else if(department.compareToIgnoreCase("QA") == 0)insertionEmployee.setDepartment(Department.QA);
		else if(department.compareToIgnoreCase("Admin") == 0)insertionEmployee.setDepartment(Department.Admin);
		else throw new EmployeeException("Incorrect department input");

		System.out.println(gender+" - "+department);
		return employeeRepository.save(insertionEmployee);
		
	}

	@Override
	public Employee getEmployeeById(Integer employeeId) throws EmployeeException {

		return employeeRepository.findById(employeeId).get();
	}

}
