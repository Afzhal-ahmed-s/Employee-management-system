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

import com.ems.model.Salary;
import com.ems.serviceImplimentation.SalaryServiceImplementation;

public class SalaryRepositoryTest {

    @Mock
    private SalaryRepository salaryRepository;

    @InjectMocks
    private SalaryServiceImplementation salaryService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindByEmployeeId_ExistingEmployeeId() {
        // Prepare the test data
        Integer employeeId = 1;
        List<Salary> salaryList = new ArrayList<>();
        Salary salary1 = new Salary();
        Salary salary2 = new Salary();
        salaryList.add(salary1);
        salaryList.add(salary2);

        // Mock the behavior of the SalaryRepository's findByEmployeeId method
        when(salaryRepository.findByEmployeeId(employeeId)).thenReturn(salaryList);

        // Invoke the findByEmployeeId method of the SalaryRepository
        List<Salary> result = salaryRepository.findByEmployeeId(employeeId);

        // Verify the interactions and assertions
        assertEquals(salaryList, result);
    }

    @Test
    public void testFindByEmployeeId_NonExistingEmployeeId() {
        // Prepare the test data
        Integer employeeId = 2;

        // Mock the behavior of the SalaryRepository's findByEmployeeId method
        when(salaryRepository.findByEmployeeId(employeeId)).thenReturn(new ArrayList<>());

        // Invoke the findByEmployeeId method of the SalaryRepository
        List<Salary> result = salaryRepository.findByEmployeeId(employeeId);

        // Verify the interactions and assertions
        assertEquals(new ArrayList<>(), result);
    }
}

