package com.ems.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ems.exception.LeaveException;
import com.ems.model.Leaves;
import com.ems.service.LeavesService;

@RestController
@RequestMapping("/leave")
public class LeavesController {

	@Autowired
	private LeavesService leavesService;
	
	@PostMapping("")
	public ResponseEntity<Leaves> addLeave(@RequestBody Leaves leaves) throws LeaveException{
		
		Leaves persistedLeave = leavesService.addLeave(leaves);
		return new ResponseEntity<Leaves>(persistedLeave, HttpStatus.CREATED);
	}
	
	@GetMapping("/{leaveId}")
	public ResponseEntity<Leaves> getLeaveById(@PathVariable Integer leaveId) throws LeaveException{
		
		Leaves leaves = leavesService.getLeaveById(leaveId);
		return new ResponseEntity<Leaves>(leaves, HttpStatus.ACCEPTED);
	}
	
	@DeleteMapping("/{leaveId}")
	public ResponseEntity<Leaves> deleteById( @PathVariable Integer leaveId) throws LeaveException{
		
		Leaves leaves = leavesService.deleteById(leaveId);
		return new ResponseEntity<Leaves>(leaves, HttpStatus.ACCEPTED);
		
	}
	
}
