package com.ems.serviceImplimentation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ems.exception.LeaveException;
import com.ems.model.Employee;
import com.ems.model.Leaves;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.LeaveRepository;
import com.ems.service.LeavesService;

@Service
public class LeavesServiceImplementation implements LeavesService{

	@Autowired
	private LeaveRepository leaveRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	String statusCode_NOT_FOUND = "HttpStatus.NOT_FOUND*_";
	
	@Override
	public Leaves addLeave(Leaves leaves) throws LeaveException {

		if(employeeRepository.findById(leaves.getEmployeeId()).isPresent() ) {
			
			List<Leaves> listOfLeaves = leaveRepository.findByEmployeeId(leaves.getEmployeeId());
			
			if(listOfLeaves != null) {
				if(listOfLeaves.size() != 0) {
					for(Leaves e : listOfLeaves) {
						if(
								leaves.getLeaveFrom().isEqual(e.getLeaveFrom()) || leaves.getLeaveFrom().isEqual(e.getLeaveTo()) 
								||	leaves.getLeaveTo().isEqual(e.getLeaveFrom()) || leaves.getLeaveTo().isEqual(e.getLeaveTo())
										|| (leaves.getLeaveFrom().isAfter(e.getLeaveFrom()) && leaves.getLeaveFrom().isBefore(e.getLeaveTo()) ) 
												|| (leaves.getLeaveTo().isAfter(e.getLeaveFrom()) && leaves.getLeaveTo().isBefore(e.getLeaveTo())) 
						  ) {
							Optional<Employee> emp = employeeRepository.findById( leaves.getEmployeeId());
							throw new LeaveException("The leaves information you gave overlaps over another leave record for this employee " + emp.get().getEmployeeID() );
						}
		
					}
				}
			}
			
			return leaveRepository.save(leaves);
		}
		else throw new LeaveException(statusCode_NOT_FOUND + "Employee ID "+ leaves.getEmployeeId() +" not found.");
	}

	@Override
	public Leaves getLeaveById(Integer leaveId) throws LeaveException {

		return leaveRepository.findById(leaveId).orElseThrow( ()-> new LeaveException(statusCode_NOT_FOUND + "Leave not found with Id "+ leaveId +".") );
	}

	@Override
	public List<Leaves> getLeaveByEmployeeId(Integer employeeId) throws LeaveException {
		if( leaveRepository.findByEmployeeId(employeeId).size() != 0 )return leaveRepository.findByEmployeeId(employeeId);
		else throw new LeaveException(statusCode_NOT_FOUND + "Employee ID "+ employeeId +" not found in leave records.");
	}

	@Override
	public Leaves getLeaveByLeaveIdAndEmployeeId(Integer employeeId, Integer leaveId) throws LeaveException {
		List<Leaves> listOfLeaves = leaveRepository.findByEmployeeId(employeeId);
		System.out.println("Check version 2");
		for(Leaves leaves : listOfLeaves) {
			if(leaves.getLeaveId() == leaveId) {
				return leaves;
			}
		}
		
		throw new LeaveException(statusCode_NOT_FOUND + "Employee ID "+employeeId +" and leave ID "+ leaveId +" doesn't match in a single record.");
	}

	@Override
	public Leaves deleteById(Integer leaveId) throws LeaveException {

		Optional<Leaves> leaves = leaveRepository.findById(leaveId);
		
		if(leaves.isPresent()) {
			leaveRepository.deleteById(leaveId);
			return leaves.get();
		}
		else throw new LeaveException(statusCode_NOT_FOUND + "Leave Id "+ leaveId + " not found.");
	}

}
