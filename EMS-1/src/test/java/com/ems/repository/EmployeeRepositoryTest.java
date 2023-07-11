package com.ems.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ems.model.Employee;
import com.ems.serviceImplimentation.EmployeeServiceImplimentation;

public class EmployeeRepositoryTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByEmployeeID_ExistingEmployeeID() {
        // Prepare the test data
        String employeeID = "NODUCO001";
        Employee employee = new Employee();
        employee.setEmployeeID(employeeID);

        // Mock the behavior of the EmployeeRepository's findByEmployeeID method
        when(employeeRepository.findByEmployeeID(employeeID)).thenReturn(Optional.of(employee));

        // Invoke the findByEmployeeID method of the EmployeeRepository
        Optional<Employee> result = employeeRepository.findByEmployeeID(employeeID);

        // Verify the interactions and assertions
        assertEquals(Optional.of(employee), result);
    }

    @Test
    public void testFindByEmployeeID_NonExistingEmployeeID() {
        // Prepare the test data
        String employeeID = "NODUCO002";

        // Mock the behavior of the EmployeeRepository's findByEmployeeID method
        when(employeeRepository.findByEmployeeID(employeeID)).thenReturn(Optional.empty());

        // Invoke the findByEmployeeID method of the EmployeeRepository
        Optional<Employee> result = employeeRepository.findByEmployeeID(employeeID);

        // Verify the interactions and assertions
        assertEquals(Optional.empty(), result);
    }
}

