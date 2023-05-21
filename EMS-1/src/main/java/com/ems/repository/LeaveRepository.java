package com.ems.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ems.model.Leaves;
import com.ems.model.Salary;

@Repository
public interface LeaveRepository extends JpaRepository<Leaves, Integer>{

	public List<Leaves> leaveId(Integer employeeId);
	public List<Leaves> findByEmployeeId(Integer employeeId);
	
}
