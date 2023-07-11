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

import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.exception.SalaryException;
import com.ems.model.Salary;
import com.ems.model.SalaryDTO;
import com.ems.service.SalaryService;

public class SalaryControllerTest {

    @Mock
    private SalaryService salaryService;

    @InjectMocks
    private SalaryController salaryController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddSalary() throws SalaryException, EmployeeException, DocumentException {
        // Prepare the test data
        Salary salary = new Salary();
        salary.setEmployeeId(1);
        Integer documentId = 123;

        Salary persistedSalary = new Salary();
        persistedSalary.setSalaryId(1);

        // Mock the behavior of the SalaryService's addSalary method
        when(salaryService.addSalary(salary, documentId)).thenReturn(persistedSalary);

        // Invoke the addSalary method of the SalaryController
        ResponseEntity<Salary> response = salaryController.addSalary(salary, documentId);

        // Verify the interactions and assertions
        assertEquals(persistedSalary, response.getBody());
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        verify(salaryService, times(1)).addSalary(salary, documentId);
    }

    @Test
    public void testGetSalaryById() throws SalaryException {
        // Prepare the test data
        Integer salaryId = 1;
        Salary salary = new Salary();
        salary.setSalaryId(salaryId);

        // Mock the behavior of the SalaryService's getSalaryById method
        when(salaryService.getSalaryById(salaryId)).thenReturn(salary);

        // Invoke the getSalaryById method of the SalaryController
        ResponseEntity<Salary> response = salaryController.getSalaryById(salaryId);

        // Verify the interactions and assertions
        assertEquals(salary, response.getBody());
        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(salaryService, times(1)).getSalaryById(salaryId);
    }

    @Test
    public void testDeleteSalaryById() throws SalaryException {
        // Prepare the test data
        Integer salaryId = 1;
        SalaryDTO salaryDTO = new SalaryDTO();

        // Mock the behavior of the SalaryService's deleteSalaryBySalaryId method
        when(salaryService.deleteSalaryBySalaryId(salaryId)).thenReturn(salaryDTO);

        // Invoke the deleteSalaryById method of the SalaryController
        ResponseEntity<SalaryDTO> response = salaryController.deleteSalaryById(salaryId);

        // Verify the interactions and assertions
        assertEquals(salaryDTO, response.getBody());
        assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
        verify(salaryService, times(1)).deleteSalaryBySalaryId(salaryId);
    }
}

