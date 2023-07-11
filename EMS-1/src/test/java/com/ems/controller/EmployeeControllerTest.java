package com.ems.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.ems.enums.Department;
import com.ems.enums.Gender;
import com.ems.model.DateRange;
import com.ems.model.Document;
import com.ems.model.Employee;
import com.ems.model.Leaves;
import com.ems.model.Salary;
import com.ems.service.EmployeeService;
import com.ems.service.LeavesService;
import com.ems.service.SalaryService;
import com.ems.serviceImplimentation.EmployeeServiceImplimentation;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;


@RunWith(MockitoJUnitRunner.class)
public class EmployeeControllerTest {

    @InjectMocks
    private EmployeeController employeeController;

    @Mock
    private EmployeeService employeeService;

    @Mock
    private SalaryService salaryService;

    @Mock
    private LeavesService leavesService;

    private MockMvc mockMvc;

    @Before
    public void setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(employeeController).build();
    }

    @Test
    public void saveEmployee_ReturnsCreatedStatus() throws Exception {
        // Arrange
        Employee employee = new Employee();
        when(employeeService.addEmployee(any(Employee.class))).thenAnswer(invocation -> {
            Employee createdEmployee = invocation.getArgument(0);
            createdEmployee.setComputationalEmployeeId(1); // setting a sample computationalEmployeeId
            return createdEmployee;
        });

        // Act
        mockMvc.perform(post("/employee")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.computationalEmployeeId").exists())
                .andExpect(jsonPath("$.computationalEmployeeId").value(1)); // verifying the value of computationalEmployeeId

        // Assert
        verify(employeeService).addEmployee(any(Employee.class));
    }


    @Test
    public void getEmployeeById_ReturnsEmployeeWithOkStatus() throws Exception {
        // Arrange
        int employeeId = 1;
        Employee employee = new Employee();
        employee.setComputationalEmployeeId(123); // Set a value for computationalEmployeeId

        when(employeeService.getEmployeeById(employeeId)).thenReturn(employee);

        // Act
        mockMvc.perform(get("/employee/{employeeId}", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.computationalEmployeeId").value(123)); // Validate the value of computationalEmployeeId

        // Assert
        verify(employeeService).getEmployeeById(employeeId);
    }


    @Test
    public void updateEmployee_ReturnsAcceptedStatus() throws Exception {
        // Arrange
        Integer employeeId = 1;
        Employee employee = new Employee();
        
        when(employeeService.updateEmployeeById(any(Employee.class), eq(employeeId))).thenReturn(employee);
        
     // Act
        mockMvc.perform(patch("/employee/{employeeId}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(employee)))
                .andExpect(status().isOk());
        // Assert
        verify(employeeService).updateEmployeeById(any(Employee.class), eq(employeeId));
    }


    @Test
    public void getSalaryByEmployeeIdWSalary_ReturnsSalariesWithOkStatus() throws Exception {
        // Arrange
    	Integer employeeId = 1;
        List<Salary> salaries = new ArrayList<>();
        when(salaryService.getSalaryByEmployeeId(employeeId)).thenReturn(salaries);

        // Act
        mockMvc.perform(get("/employee/{employeeId}/salary", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Assert
        verify(salaryService).getSalaryByEmployeeId(employeeId);
    }

    @Test
    public void getSalaryByEmployeeIdAndSalaryId_ReturnsSalaryWithOkStatus() throws Exception {
        // Arrange
    	Integer employeeId = 1;
    	Integer salaryId = 1;
        Salary salary = new Salary();
        when(salaryService.getSalaryBySalaryIdAndEmployeeId(employeeId, salaryId)).thenReturn(salary);

        // Act
        mockMvc.perform(get("/employee/{employeeId}/salary/{salaryId}", employeeId, salaryId))
                .andExpect(status().isOk());

        // Assert
        verify(salaryService).getSalaryBySalaryIdAndEmployeeId(employeeId, salaryId);
    }

    @Test
    public void getLeaveByEmployeeIdWLeave_ReturnsLeavesWithOkStatus() throws Exception {
        // Arrange
    	Integer employeeId = 1;
        List<Leaves> leaves = new ArrayList<>();
        when(leavesService.getLeaveByEmployeeId(employeeId)).thenReturn(leaves);

        // Act
        mockMvc.perform(get("/employee/{employeeId}/leave", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Assert
        verify(leavesService).getLeaveByEmployeeId(employeeId);
    }

    @Test
    public void getLeaveByEmployeeIdAndLeaveId_ReturnsLeaveWithOkStatus() throws Exception {
        // Arrange
        Integer employeeId = 1;
        Integer leaveId = 1;
        Leaves leave = new Leaves();
        when(leavesService.getLeaveByLeaveIdAndEmployeeId(employeeId, leaveId)).thenReturn(leave);

        // Act
        mockMvc.perform(get("/employee/{employeeId}/leave/{leaveId}", employeeId, leaveId))
                .andExpect(status().isOk());

        // Assert
        verify(leavesService).getLeaveByLeaveIdAndEmployeeId(employeeId, leaveId);
    }

    @Test
    public void getAllDocumentsOfAnEmployeeByEmployeeId_ReturnsDocumentsWithOkStatus() throws Exception {
        // Arrange
        int employeeId = 1;
        List<Document> documents = new ArrayList<>();
        when(employeeService.getAlldocumentsById(employeeId)).thenReturn(documents);

        // Act
        mockMvc.perform(get("/employee/{employeeId}/getDocuments", employeeId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());

        // Assert
        verify(employeeService).getAlldocumentsById(employeeId);
    }

    @Test
    public void getListOfLeavesForEmployeeWithinDateRange_ReturnsLeavesWithAcceptedStatus() throws Exception {
        // Arrange
        int employeeId = 1;
        DateRange dateRange = new DateRange();
        List<Leaves> leaves = new ArrayList<>();
        when(employeeService.getAllLeavesByEmployeeIdWithinDateRange(employeeId, dateRange)).thenReturn(leaves);

        // Act
        mockMvc.perform(post("/employee/{employeeId}/getLeaves", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(new ObjectMapper().writeValueAsString(dateRange)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$").isArray());

        // Assert
        verify(employeeService).getAllLeavesByEmployeeIdWithinDateRange(employeeId, dateRange);
    }
}

