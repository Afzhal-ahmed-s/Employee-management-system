package com.ems.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import com.ems.enums.Department;
import com.ems.enums.DocumentType;
import com.ems.enums.Gender;
import com.ems.exception.DocumentException;
import com.ems.exception.EmployeeException;
import com.ems.model.DateRange;
import com.ems.model.Document;
import com.ems.model.Employee;
import com.ems.model.Leaves;
import com.ems.model.NextNumberGenerator;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.LeaveRepository;
import com.ems.serviceImplimentation.DocumentServiceImplementation;
import com.ems.serviceImplimentation.EmployeeServiceImplimentation;
import com.ems.serviceImplimentation.LeavesServiceImplementation;

@RunWith(MockitoJUnitRunner.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImplimentation employeeService;
    
    @InjectMocks
    private DocumentServiceImplementation documentService;
    
    @InjectMocks
    private LeavesServiceImplementation leaveService;
    
    @Mock
    private LeaveRepository leaveRepository;
    
    @Mock
    private NextNumberGenerator nextNumberGenerator;

    @Test
    public void testGetEmployeeById_WhenEmployeeExists() throws EmployeeException {
        // Arrange
        Integer employeeId = 1;
        Employee employee = new Employee();
        employee.setComputationalEmployeeId(employeeId);
        Optional<Employee> employeeOptional = Optional.of(employee);

        // Mock the behavior of employeeRepository.findById()
        when(employeeRepository.findById(employeeId)).thenReturn(employeeOptional);

        // Act
        Employee result = employeeService.getEmployeeById(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(employeeId, result.getComputationalEmployeeId());
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    public void testGetEmployeeById_WhenEmployeeDoesNotExist() {
        // Arrange
        int employeeId = 456;

        // Mock the behavior of employeeRepository.findById()
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act and Assert
        assertThrows(EmployeeException.class, () -> employeeService.getEmployeeById(employeeId));
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    public void testGetAllDocumentsById_WhenEmployeeExistsAndHasDocuments() throws DocumentException, EmployeeException {
        // Arrange
        Integer employeeId = 1;
        List<Document> documents = new ArrayList<>();
        documents.add(new Document());
        documents.add(new Document());

        Employee employee = new Employee();
        employee.setDocuments(documents);

        Optional<Employee> employeeOptional = Optional.of(employee);

        // Mock the behavior of employeeRepository.findById()
        when(employeeRepository.findById(employeeId)).thenReturn(employeeOptional);

        // Act
        List<Document> result = employeeService.getAlldocumentsById(employeeId);

        // Assert
        assertNotNull(result);
        assertEquals(documents.size(), result.size());
        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    public void testGetAllDocumentsById_WhenEmployeeExistsAndHasNoDocuments() {
        // Arrange
        int employeeId = 456;

        Employee employee = new Employee();
        employee.setDocuments(new ArrayList<>());

        Optional<Employee> employeeOptional = Optional.of(employee);
        String expectedResult = "HttpStatus.NOT_FOUND*_Employee with Id "+ employeeId +" has has no documents in his records.";

        // Mock the behavior of employeeRepository.findById()
        when(employeeRepository.findById(employeeId)).thenReturn(employeeOptional);

        // Act and Assert
        DocumentException exception = assertThrows(DocumentException.class, () -> employeeService.getAlldocumentsById(employeeId));
        assertEquals(expectedResult, exception.getMessage());

        verify(employeeRepository, times(1)).findById(employeeId);
    }

    @Test
    public void testGetAllDocumentsById_WhenEmployeeDoesNotExist() {
        // Arrange
        int employeeId = 789;

        // Mock the behavior of employeeRepository.findById()
        when(employeeRepository.findById(employeeId)).thenReturn(Optional.empty());

        // Act and Assert
        EmployeeException exception = assertThrows(EmployeeException.class, () -> employeeService.getAlldocumentsById(employeeId));

    }


    @Test
    public void testAddEmployee_WithValidData() throws EmployeeException {
        // Arrange
        Employee employee = new Employee();
        employee.setGender(Gender.MALE.toString());
        employee.setDepartment(Department.Development.toString());

        when(nextNumberGenerator.getNextNumber()).thenReturn(1);
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);

        // Act
        Employee result = employeeService.addEmployee(employee);

        // Assert
        assertNotNull(result);
        assertEquals(Gender.MALE.toString(), result.getGender());
        assertEquals(Department.Development.toString(), result.getDepartment());

        verify(nextNumberGenerator, times(1)).getNextNumber();
        verify(employeeRepository, times(1)).save(employee);
    }

    @Test
    public void testAddEmployee_WithInvalidGender() {
        // Arrange
        Employee employee = new Employee();
        employee.setGender("random");
        employee.setDepartment(Department.Development.toString());

        // Act and Assert
        EmployeeException exception = assertThrows(EmployeeException.class, () -> employeeService.addEmployee(employee));
        assertEquals("Improper gender options provided.", exception.getMessage());

        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    public void testAddEmployee_WithInvalidDepartment() {
        // Arrange
        Employee employee = new Employee();
        employee.setGender(Gender.MALE.toString());
        employee.setDepartment("actor");

        // Act and Assert
        EmployeeException exception = assertThrows(EmployeeException.class, () -> employeeService.addEmployee(employee));
        assertEquals("Improper department options provided.", exception.getMessage());

        verifyNoMoreInteractions(employeeRepository);
    }

    @Test
    public void testGetAllLeavesByEmployeeIdWithinDateRange() throws EmployeeException {
        // Arrange
        Integer employeeID = 123;
        LocalDate leaveFrom = LocalDate.of(2023, 1, 1);
        LocalDate leaveTo = LocalDate.of(2023, 1, 31);
        DateRange dateRange = new DateRange(leaveFrom, leaveTo);

        Employee employee = new Employee();
        employee.setComputationalEmployeeId(employeeID);
        when(employeeRepository.findById(employeeID)).thenReturn(Optional.of(employee));

        List<Leaves> allLeaves = new ArrayList<>();
        Leaves leave1 = new Leaves();
        leave1.setEmployeeId(employeeID);
        leave1.setLeaveFrom(leaveFrom);
        leave1.setLeaveTo(leaveTo);
        allLeaves.add(leave1);

        Leaves leave2 = new Leaves();
        leave2.setEmployeeId(456);
        leave2.setLeaveFrom(LocalDate.of(2023, 2, 1));
        leave2.setLeaveTo(LocalDate.of(2023, 2, 28));
        allLeaves.add(leave2);

        when(leaveRepository.findAll()).thenReturn(allLeaves);

        // Act
        List<Leaves> result = employeeService.getAllLeavesByEmployeeIdWithinDateRange(employeeID, dateRange);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(employeeID, result.get(0).getEmployeeId());
        assertEquals(leaveFrom, result.get(0).getLeaveFrom());
        assertEquals(leaveTo, result.get(0).getLeaveTo());

        verify(employeeRepository, times(1)).findById(employeeID);
    }

    @Test
    public void testGetAllLeavesByEmployeeIdWithinDateRange_WithInvalidEmployeeId() {
        // Arrange
        Integer employeeID = 123;
        LocalDate leaveFrom = LocalDate.of(2023, 1, 1);
        LocalDate leaveTo = LocalDate.of(2023, 1, 31);
        DateRange dateRange = new DateRange(leaveFrom, leaveTo);
        String expectedResult = "HttpStatus.NOT_FOUND*_No employee found with ID "+ employeeID;

        when(employeeRepository.findById(employeeID)).thenReturn(Optional.empty());

        // Act and Assert
        EmployeeException exception = assertThrows(EmployeeException.class, () ->
        employeeService.getAllLeavesByEmployeeIdWithinDateRange(employeeID, dateRange));
        assertEquals(expectedResult, exception.getMessage());

        verify(employeeRepository, times(1)).findById(employeeID);
    }

    @Test
    public void testUpdateEmployeeById() throws EmployeeException, DocumentException {
        // Arrange
        Integer employeeId = 1;
        Employee employee = new Employee();
        employee.setDateOfBirth(LocalDate.now());
        employee.setDateOfJoining(LocalDate.now());
        employee.setDateOfLeaving(LocalDate.now());
        employee.setDepartment(Department.Development.toString());
        employee.setEmailAddress("test@example.com");
        employee.setFirstName("John");
        employee.setLastName("Doe");
        employee.setGender(Gender.MALE.toString());
        employee.setPhoneNumber("1234567890");

        Employee existingEmployee = new Employee();
        existingEmployee.setEmployeeID("NODUCO1");
        existingEmployee.setDateOfBirth(LocalDate.of(1990, 1, 1));
        existingEmployee.setDateOfJoining(LocalDate.of(2020, 1, 1));
        existingEmployee.setDateOfLeaving(LocalDate.of(2022, 1, 1));
        existingEmployee.setDepartment(Department.QA.toString());
        existingEmployee.setEmailAddress("old@example.com");
        existingEmployee.setFirstName("Jane");
        existingEmployee.setLastName("Smith");
        existingEmployee.setGender(Gender.FEMALE.toString());
        existingEmployee.setPhoneNumber("9876543210");

        List<Document> existingDocuments = new ArrayList<>();
        Document document = new Document();
        document.setType(DocumentType.PASSPORT.toString());
        existingDocuments.add(document);
        existingEmployee.setDocuments(existingDocuments);

        when(employeeRepository.findById(employeeId)).thenReturn(Optional.of(existingEmployee));
        when(employeeRepository.save(any(Employee.class))).thenReturn(existingEmployee);

        // Act
        Employee updatedEmployee = employeeService.updateEmployeeById(employee, employeeId);

        // Assert
        assertNotNull(updatedEmployee);
        assertEquals(employee.getDateOfBirth(), updatedEmployee.getDateOfBirth());
        assertEquals(employee.getDateOfJoining(), updatedEmployee.getDateOfJoining());
        assertEquals(employee.getDateOfLeaving(), updatedEmployee.getDateOfLeaving());
        assertEquals(employee.getDepartment().toString(), updatedEmployee.getDepartment());
        assertEquals(employee.getEmailAddress(), updatedEmployee.getEmailAddress());
        assertEquals(employee.getFirstName(), updatedEmployee.getFirstName());
        assertEquals(employee.getLastName(), updatedEmployee.getLastName());
        assertEquals(employee.getGender().toString(), updatedEmployee.getGender());
        assertEquals(employee.getPhoneNumber(), updatedEmployee.getPhoneNumber());

        verify(employeeRepository, times(1)).findById(employeeId);
        verify(employeeRepository, times(1)).save(existingEmployee);
    }
    
    
}


