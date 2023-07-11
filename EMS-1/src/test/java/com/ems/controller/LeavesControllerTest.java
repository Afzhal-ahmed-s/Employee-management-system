package com.ems.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ems.exception.LeaveException;
import com.ems.model.Leaves;
import com.ems.service.LeavesService;

public class LeavesControllerTest {

    @Mock
    private LeavesService leavesService;

    @InjectMocks
    private LeavesController leavesController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddLeave() throws LeaveException {
        // Prepare the test data
        Leaves leaves = new Leaves();
        leaves.setEmployeeId(1);

        Leaves persistedLeave = new Leaves();
        persistedLeave.setLeaveId(1);

        // Mock the behavior of the LeavesService's addLeave method
        when(leavesService.addLeave(leaves)).thenReturn(persistedLeave);

        // Invoke the addLeave method of the LeavesController
        ResponseEntity<Leaves> response = leavesController.addLeave(leaves);

        // Verify the interactions and assertions
        assertEquals(persistedLeave, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(leavesService, times(1)).addLeave(leaves);
    }

    @Test
    public void testGetLeaveById() throws LeaveException {
        // Prepare the test data
        Integer leaveId = 1;
        Leaves leaves = new Leaves();
        leaves.setLeaveId(leaveId);

        // Mock the behavior of the LeavesService's getLeaveById method
        when(leavesService.getLeaveById(leaveId)).thenReturn(leaves);

        // Invoke the getLeaveById method of the LeavesController
        ResponseEntity<Leaves> response = leavesController.getLeaveById(leaveId);

        // Verify the interactions and assertions
        assertEquals(leaves, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(leavesService, times(1)).getLeaveById(leaveId);
    }

    @Test
    public void testDeleteById() throws LeaveException {
        // Prepare the test data
        Integer leaveId = 1;
        Leaves leaves = new Leaves();

        // Mock the behavior of the LeavesService's deleteById method
        when(leavesService.deleteById(leaveId)).thenReturn(leaves);

        // Invoke the deleteById method of the LeavesController
        ResponseEntity<Leaves> response = leavesController.deleteById(leaveId);

        // Verify the interactions and assertions
        assertEquals(leaves, response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(leavesService, times(1)).deleteById(leaveId);
    }
}

