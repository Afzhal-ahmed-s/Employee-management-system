package com.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.model.Salary;

@Repository
public interface SalaryRepository extends JpaRepository<Salary, Integer>{

	public List<Salary> findByEmployeeId(Integer employeeId);
	
}
