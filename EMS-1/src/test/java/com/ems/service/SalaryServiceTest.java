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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.exception.SalaryException;
import com.ems.model.Document;
import com.ems.model.Employee;
import com.ems.model.Salary;
import com.ems.model.SalaryDTO;
import com.ems.repository.DocumentRepository;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.SalaryRepository;
import com.ems.serviceImplimentation.SalaryServiceImplementation;

@SpringBootTest
public class SalaryServiceTest {

    @InjectMocks
    private SalaryServiceImplementation salaryService;

    @Mock
    private SalaryRepository salaryRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private DocumentRepository documentRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAddSalary_Successful() throws SalaryException, EmployeeException, DocumentException {
        // Arrange
        Salary salary = createSampleSalary();
        int documentId = 1;

        when(employeeRepository.findById(salary.getEmployeeId())).thenReturn(Optional.of(new Employee()));
        when(documentRepository.findById(documentId)).thenReturn(Optional.of(new Document()));
        when(salaryRepository.save(any(Salary.class))).thenReturn(salary);

        // Act
        Salary result = salaryService.addSalary(salary, documentId);

        // Assert
        assertEquals(salary, result);
        assertNotNull(result.getSalarySlip());
        verify(documentRepository, times(2)).findById(documentId); // Called twice in the method
        verify(salaryRepository, times(1)).save(salary);
    }

    @Test
    public void testAddSalary_EmployeeNotFound() {
        // Arrange
        Salary salary = createSampleSalary();
        int documentId = 1;

        when(employeeRepository.findById(salary.getEmployeeId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EmployeeException.class, () -> salaryService.addSalary(salary, documentId));
        verify(documentRepository, never()).findById(anyInt());
        verify(salaryRepository, never()).save(any(Salary.class));
    }
    
    @Test
    public void testAddSalary_DocumentNotFound() throws SalaryException, EmployeeException, DocumentException {
        // Arrange
        SalaryDTO salaryDto = new SalaryDTO();
        salaryDto.setEmployeeId(1);
        // Set other properties of the salaryDto object as needed for testing

        int documentId = 1;
        int employeeId = salaryDto.getEmployeeId();

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty()); // Mocking the employee not found scenario
        when(documentRepository.findById(documentId)).thenReturn(Optional.empty()); // Mocking the document not found scenario

        // Convert SalaryDTO to Salary object
        Salary salary = new Salary();
        salary.setEmployeeId(salaryDto.getEmployeeId());
        // Set other properties of the salary object based on the salaryDto object

        // Act & Assert
        assertThrows(EmployeeException.class, () -> salaryService.addSalary(salary, documentId));
        verify(salaryRepository, never()).save(any(Salary.class));
    }


    @Test
    public void testGetSalaryById_Successful() throws SalaryException {
        // Arrange
        Salary salary = createSampleSalary();
        int salaryId = 1;

        when(salaryRepository.findById(salaryId)).thenReturn(Optional.of(salary));

        // Act
        Salary result = salaryService.getSalaryById(salaryId);

        // Assert
        assertEquals(salary, result);
        verify(salaryRepository, times(1)).findById(salaryId);
    }

    @Test
    public void testGetSalaryById_NotFound() {
        // Arrange
        int salaryId = 1;

        when(salaryRepository.findById(salaryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SalaryException.class, () -> salaryService.getSalaryById(salaryId));
        verify(salaryRepository, times(1)).findById(salaryId);
    }

    @Test
    public void testGetSalaryByEmployeeId_Successful() throws EmployeeException {
        // Arrange
        Salary salary = createSampleSalary();
        int employeeId = 1;

        when(salaryRepository.findByEmployeeId(employeeId)).thenReturn(Arrays.asList(salary));

        // Act
        List<Salary> result = salaryService.getSalaryByEmployeeId(employeeId);

        // Assert
        assertEquals(1, result.size());
        assertEquals(salary, result.get(0));
        verify(salaryRepository, times(2)).findByEmployeeId(employeeId);
    }

    @Test
    public void testGetSalaryByEmployeeId_NotFound() {
        // Arrange
        int employeeId = 1;

        when(salaryRepository.findByEmployeeId(employeeId)).thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(EmployeeException.class, () -> salaryService.getSalaryByEmployeeId(employeeId));
        verify(salaryRepository, times(1)).findByEmployeeId(employeeId);
    }

    @Test
    public void testGetSalaryBySalaryIdAndEmployeeId_Successful() throws SalaryException {
        // Arrange
        Salary salary = createSampleSalary();
        int employeeId = 1;
        int salaryId = 1;

        when(salaryRepository.findByEmployeeId(employeeId)).thenReturn(Arrays.asList(salary));

        // Act
        Salary result = salaryService.getSalaryBySalaryIdAndEmployeeId(employeeId, salaryId);

        // Assert
        assertEquals(salary, result);
        verify(salaryRepository, times(1)).findByEmployeeId(employeeId);
    }

    @Test
    public void testGetSalaryBySalaryIdAndEmployeeId_NotFound() {
        // Arrange
        Salary salary = createSampleSalary();
        int employeeId = 1;
        int salaryId = 105;

        when(salaryRepository.findByEmployeeId(employeeId)).thenReturn(Arrays.asList());

        // Act & Assert
        assertThrows(SalaryException.class, () -> salaryService.getSalaryBySalaryIdAndEmployeeId(employeeId, salaryId));
        verify(salaryRepository, times(1)).findByEmployeeId(employeeId);
    }

    @Test
    public void testDeleteSalaryBySalaryId_Successful() throws SalaryException {
        // Arrange
        Salary salary = createSampleSalary();
        int salaryId = 1;

        when(salaryRepository.findById(salaryId)).thenReturn(Optional.of(salary));

        // Act
        SalaryDTO result = salaryService.deleteSalaryBySalaryId(salaryId);

        // Assert
        assertEquals(salaryId, result.getSalaryId());
        verify(salaryRepository, times(1)).findById(salaryId);
        verify(salaryRepository, times(1)).deleteById(salaryId);
    }

    @Test
    public void testDeleteSalaryBySalaryId_NotFound() {
        // Arrange
        int salaryId = 1;

        when(salaryRepository.findById(salaryId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(SalaryException.class, () -> salaryService.deleteSalaryBySalaryId(salaryId));
        verify(salaryRepository, times(1)).findById(salaryId);
        verify(salaryRepository, never()).deleteById(anyInt());
    }

    private Salary createSampleSalary() {
        Salary salary = new Salary();
        salary.setSalaryId(1);
        salary.setEmployeeId(1);
        salary.setBankAccountName("John Cena");
        salary.setBankAccountNumber("1234567890");
        salary.setBankAccountIFSC("ABC123");
        salary.setTransactionId("TXN123");
        salary.setCreditDate(LocalDate.now());
        salary.setSalaryYear(2023);
        salary.setSalaryMonth("June");
        salary.setSalaryTotal(5000.0);
        salary.setSalaryAmount(4000.0);
        salary.setSalaryCurrency("USD");
        salary.setTdsAmount(500.0);
        salary.setDeductionAmount(500.0);
        salary.setSalarySlip(new Document());
        salary.setNotes("Sample salary notes");

        return salary;
    }
}


