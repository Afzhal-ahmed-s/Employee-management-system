package com.ems.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ems.exception.LeaveException;
import com.ems.model.Employee;
import com.ems.model.Leaves;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.LeaveRepository;
import com.ems.serviceImplimentation.LeavesServiceImplementation;



public class LeavesServiceTest {

	@Mock
	private LeaveRepository leaveRepository;

	@Mock
	private EmployeeRepository employeeRepository;

	@InjectMocks
	private LeavesServiceImplementation leavesService;

	@SuppressWarnings("deprecation")
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

		@BeforeEach
	    public void setup() {
	        MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testAddLeave_EmployeeNotFound() {
	        // Mock employeeRepository.findById to return an empty Optional
	        when(employeeRepository.findById(anyInt())).thenReturn(Optional.empty());

	        // Create a Leaves object for testing
	        Leaves leaves = createLeave(3, LocalDate.of(2023, 6, 3), LocalDate.of(2023, 6, 8));

	        // Invoke the addLeave method and expect a LeaveException to be thrown
	        assertThrows(LeaveException.class, () -> leavesService.addLeave(leaves));

	        // Verify that leaveRepository.save was not called
	        verify(leaveRepository, never()).save(any(Leaves.class));
	    }

	    @Test
	    public void testAddLeave_OverlappingLeaves() {
	        // Mock employeeRepository.findById to return a present Optional
	        when(employeeRepository.findById(anyInt())).thenReturn(Optional.of(new Employee()));

	        // Mock leaveRepository.findByEmployeeId to return a list of leaves with overlapping dates
	        when(leaveRepository.findByEmployeeId(anyInt())).thenReturn(Arrays.asList(
	                createLeave(1, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 5)),
	                createLeave(2, LocalDate.of(2023, 6, 10), LocalDate.of(2023, 6, 15))
	        ));

	        // Create a Leaves object for testing with overlapping dates
	        Leaves leaves = createLeave(3, LocalDate.of(2023, 6, 4), LocalDate.of(2023, 6, 12));

	        // Invoke the addLeave method and expect a LeaveException to be thrown
	        assertThrows(LeaveException.class, () -> leavesService.addLeave(leaves));

	        // Verify that leaveRepository.save was not called
	        verify(leaveRepository, never()).save(any(Leaves.class));
	    }


	@Test
	public void testGetLeaveById_Successful() throws LeaveException {
		// Mock data
		int leaveId = 1;
		Leaves leaves = new Leaves();

		// Mock behavior
		when(leaveRepository.findById(leaveId)).thenReturn(Optional.of(leaves));

		// Test the method
		Leaves result = leavesService.getLeaveById(leaveId);

		// Verify the interactions and assertions
		verify(leaveRepository).findById(leaveId);
		org.junit.Assert.assertEquals(leaves, result);
	}

	@Test(expected = LeaveException.class)
	public void testGetLeaveById_LeaveException() throws LeaveException {
		// Mock data
		int leaveId = 1;

		// Mock behavior
		when(leaveRepository.findById(leaveId)).thenReturn(Optional.empty());

		// Test the method
		leavesService.getLeaveById(leaveId);
	}
	
	@Test
	public void testGetLeaveByEmployeeId_Successful() throws LeaveException {
	    // Mock data
	    int employeeId = 1;
	    List<Leaves> listOfLeaves = new ArrayList<>();
	    listOfLeaves.add(new Leaves());

	    // Mock behavior
	    when(leaveRepository.findByEmployeeId(employeeId)).thenReturn(listOfLeaves);

	    // Test the method
	    List<Leaves> result = leavesService.getLeaveByEmployeeId(employeeId);

	    org.junit.Assert.assertEquals(listOfLeaves, result);
	}


	@Test(expected = LeaveException.class)
	public void testGetLeaveByEmployeeId_LeaveException() throws LeaveException {
		// Mock data
		int employeeId = 1;

		// Mock behavior
		when(leaveRepository.findByEmployeeId(employeeId)).thenReturn(new ArrayList<>());

		// Test the method
		leavesService.getLeaveByEmployeeId(employeeId);
	}



	@Test(expected = LeaveException.class)
	public void testGetLeaveByLeaveIdAndEmployeeId_LeaveException() throws LeaveException {
		// Mock data
		int employeeId = 1;
		int leaveId = 1;

		// Mock behavior
		when(leaveRepository.findByEmployeeId(employeeId)).thenReturn(new ArrayList<>());

		// Test the method
		leavesService.getLeaveByLeaveIdAndEmployeeId(employeeId, leaveId);
	}

	@Test
	public void testDeleteById_Successful() throws LeaveException {
		// Mock data
		int leaveId = 1;
		Optional<Leaves> leaves = Optional.of(new Leaves());

		// Mock behavior
		when(leaveRepository.findById(leaveId)).thenReturn(leaves);

		// Test the method
		Leaves result = leavesService.deleteById(leaveId);

		// Verify the interactions and assertions
		verify(leaveRepository).findById(leaveId);
		verify(leaveRepository).deleteById(leaveId);
		org.junit.Assert.assertEquals(leaves.get(), result);
	}

	@Test(expected = LeaveException.class)
	public void testDeleteById_LeaveException() throws LeaveException {
		// Mock data
		int leaveId = 1;

		// Mock behavior
		when(leaveRepository.findById(leaveId)).thenReturn(Optional.empty());

		// Test the method
		leavesService.deleteById(leaveId);
	}
	
	//wrong ones
	
	@Test
	public void testGetLeaveByLeaveIdAndEmployeeId_Successful() throws LeaveException {
	    // Mock data
	    int employeeId = 1;
	    int leaveId = 12;
	    Leaves leave = new Leaves();
	    leave.setEmployeeId(employeeId);
	    leave.setLeaveId(leaveId);

	    // Mock behavior
	    when(leaveRepository.findByEmployeeId(employeeId)).thenReturn(Collections.singletonList(leave));

	    // Test the method
	    Leaves result = leavesService.getLeaveByLeaveIdAndEmployeeId(employeeId, leaveId);

	    // Verify the interactions and assertions
	    verify(leaveRepository).findByEmployeeId(employeeId);
	    assertEquals(leaveId, result.getLeaveId());
	}

	
    @Test
    public void testAddLeave_Successful() throws LeaveException {
    	Optional<Employee> emp = createEmployee();
        // Mock employeeRepository.findById to return a present Optional
        when(employeeRepository.findById(anyInt())).thenReturn(emp);

        // Mock leaveRepository.findByEmployeeId to return a list of leaves
        when(leaveRepository.findByEmployeeId(1)).thenReturn(Arrays.asList(
                createLeave(1, LocalDate.of(2023, 6, 1), LocalDate.of(2023, 6, 5))
        ));

        // Mock leaveRepository.save to return the input leaves object
        when(leaveRepository.save(any(Leaves.class))).thenReturn(new Leaves());

        
        LocalDate sd = LocalDate.of(1900, 6, 3);
        LocalDate ed = LocalDate.of(2100, 6, 3);
        // Create a Leaves object for testing
        Leaves leaves = createLeave(3, generateRandomDate(sd, ed), generateRandomDate(sd, ed));

        // Invoke the addLeave method
        Leaves result = leavesService.addLeave(leaves);

        // Verify that leaveRepository.save was called
        verify(leaveRepository).save(leaves);

        // Verify that the result is not null
        assertNotNull(result);
    }
    
    private Optional<Employee> createEmployee() {
    	Employee employee = new Employee();
    	employee.setComputationalEmployeeId(1);
    	//employee.setEmployeeID("EMP001");
    	employee.setFirstName("John");
    	employee.setLastName("Doe");
    	employee.setPhoneNumber("1234567890");
    	employee.setDateOfBirth(LocalDate.of(1990, 1, 1));
    	employee.setDateOfJoining(LocalDate.of(2020, 1, 1));
    	employee.setDateOfLeaving(null); // Assuming the employee is still working
    	employee.setGender("Male");
    	employee.setEmailAddress("john.doe@example.com");
    	//employee.setDocuments(new ArrayList<>()); // Assuming no documents at the moment
    	employee.setDepartment("Development");

    	
    	return Optional.of(employee);
    }
    
    // Helper method to create a Leaves object
    private Leaves createLeave(Integer leaveId, LocalDate leaveFrom, LocalDate leaveTo) {
        Leaves leaves = new Leaves();
        leaves.setLeaveId(leaveId);
        leaves.setLeaveFrom(leaveFrom);
        leaves.setLeaveTo(leaveTo);
        leaves.setEmployeeId(1);
        return leaves;
    }
    
    private LocalDate generateRandomDate(LocalDate startDate, LocalDate endDate) {
        long startEpochDay = startDate.toEpochDay();
        long endEpochDay = endDate.toEpochDay();
        long randomEpochDay = ThreadLocalRandom.current().nextLong(startEpochDay, endEpochDay + 1);
        return LocalDate.ofEpochDay(randomEpochDay);
    }
	
}
