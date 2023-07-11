package com.ems.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ems.model.Leaves;
import com.ems.serviceImplimentation.LeavesServiceImplementation;

public class LeaveRepositoryTest {

    @Mock
    private LeaveRepository leaveRepository;

    @InjectMocks
    private LeavesServiceImplementation leaveService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByEmployeeId_ExistingEmployeeId() {
        // Prepare the test data
        Integer employeeId = 1;
        List<Leaves> leavesList = new ArrayList<>();
        Leaves leave1 = new Leaves();
        Leaves leave2 = new Leaves();
        leavesList.add(leave1);
        leavesList.add(leave2);

        // Mock the behavior of the LeaveRepository's findByEmployeeId method
        when(leaveRepository.findByEmployeeId(employeeId)).thenReturn(leavesList);

        // Invoke the findByEmployeeId method of the LeaveRepository
        List<Leaves> result = leaveRepository.findByEmployeeId(employeeId);

        // Verify the interactions and assertions
        assertEquals(leavesList, result);
    }

    @Test
    public void testFindByEmployeeId_NonExistingEmployeeId() {
        // Prepare the test data
        Integer employeeId = 2;

        // Mock the behavior of the LeaveRepository's findByEmployeeId method
        when(leaveRepository.findByEmployeeId(employeeId)).thenReturn(new ArrayList<>());

        // Invoke the findByEmployeeId method of the LeaveRepository
        List<Leaves> result = leaveRepository.findByEmployeeId(employeeId);

        // Verify the interactions and assertions
        assertEquals(new ArrayList<>(), result);
    }
}

