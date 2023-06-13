package com.ems.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.ems.exception.EmployeeException;
import com.ems.model.Employee;
import com.ems.repository.EmployeeRepository;
import com.ems.serviceImplimentation.EmployeeServiceImplimentation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeServiceTest {
    private EmployeeServiceImplimentation employeeService;
    private EmployeeRepository employeeRepository;

    @Before
    public void setUp() {
        employeeRepository = mock(EmployeeRepository.class);
        employeeService = new EmployeeServiceImplimentation();
    }

    @Test
    public void testGetEmployeeById_WhenEmployeeExists() throws EmployeeException {
        // Arrange
        Integer employeeId = 1;
        Employee employee = new Employee();
        employee.setEmployeeID("NODUCO1");
        employee.setComputationalEmployeeId(employeeId);
        employee.setFirstName(Mockito.anyString());
        employee.setLastName(Mockito.anyString());
        employee.setPhoneNumber("12345");
        employee.setDateOfBirth(Mockito.any(LocalDate.class));
        employee.setDateOfJoining(Mockito.any(LocalDate.class));
        employee.setDateOfLeaving(Mockito.any(LocalDate.class));
        employee.setGender("male");
        employee.setEmailAddress("a@mail.com");
        employee.setDocuments(null);
        employee.setDepartment("Development");
        Optional<Employee> optionalEmployee = Optional.of(employee);

        // Mock the behavior of employeeRepository.findById()
        when(employeeRepository.findById(employeeId)).thenReturn(optionalEmployee);

        // Act
        Employee result = employeeService.getEmployeeById(employeeId);
        System.out.println("junit test");

        // Assert
        assertNotNull(result);
        assertEquals(employee, result);

        // Verify that employeeRepository.findById() was called with the correct employeeId
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test(expected = EmployeeException.class)
    public void testGetEmployeeById_WhenEmployeeDoesNotExist() throws EmployeeException {
        // Arrange
        int employeeId = 456;

        // Mock the behavior of employeeRepository.findById()
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act
        employeeService.getEmployeeById(employeeId);
        
        // The test is expected to throw an EmployeeException, so no assert is needed.
    }
}

