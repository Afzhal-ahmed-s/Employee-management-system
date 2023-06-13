package com.ems.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.ems.model.Employee;

@Service
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	Optional<Employee> findByEmployeeID(String employeeID);
	
}
