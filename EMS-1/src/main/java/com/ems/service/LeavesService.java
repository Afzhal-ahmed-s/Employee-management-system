package com.ems.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.ems.exception.LeaveException;
import com.ems.model.Leaves;

@Service
public interface LeavesService {

	
	public Leaves addLeave(Leaves leaves) throws LeaveException;
	public Leaves getLeaveById(Integer leaveId) throws LeaveException;
	public List<Leaves> getLeaveByEmployeeId(Integer employeeId)throws LeaveException;
	public Leaves getLeaveByLeaveIdAndEmployeeId(Integer leaveId, Integer employeeId) throws LeaveException;
	public Leaves deleteById(Integer leaveId) throws LeaveException;
}
