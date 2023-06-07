package com.ems.serviceImplimentation;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.ems.exception.EmployeeException;
import com.ems.exception.SalaryException;
import com.ems.model.Salary;
import com.ems.model.SalaryDTO;
import com.ems.repository.EmployeeRepository;
import com.ems.repository.SalaryRepository;
import com.ems.service.SalaryService;

@Service
public class SalaryServiceImplementation implements SalaryService{

	@Autowired
	private SalaryRepository salaryRepository;
	
	@Autowired
	private EmployeeRepository employeeRepository;
	
	String statusCode_NOT_FOUND = "HttpStatus.NOT_FOUND*_";
	
	@Override
	public Salary addSalary(Salary salary) throws SalaryException, EmployeeException {
		System.out.println("Check: "+ employeeRepository.findById(salary.getEmployeeId()) );
		if(employeeRepository.findById(salary.getEmployeeId()).isPresent() )return salaryRepository.save(salary);
		else throw new EmployeeException(statusCode_NOT_FOUND + "Employee ID "+ salary.getEmployeeId() + " not found.");
	}

	@Override
	public Salary getSalaryById(Integer salaryId) throws SalaryException {

		return salaryRepository.findById(salaryId).orElseThrow( ()-> new SalaryException(statusCode_NOT_FOUND +"Salary not found with salary ID "+ salaryId +".") );
	}

	@Override
	public List<Salary> getSalaryByEmployeeId(Integer employeeId) throws EmployeeException {

		if( salaryRepository.findByEmployeeId(employeeId).size() != 0 )return salaryRepository.findByEmployeeId(employeeId);
		else throw new EmployeeException(statusCode_NOT_FOUND + "Employee ID "+ employeeId +" not found in salary records.");
	}

	@Override
	public Salary getSalaryBySalaryIdAndEmployeeId(Integer employeeId, Integer salaryId) throws SalaryException {

		List<Salary> listOfsalaries = salaryRepository.findByEmployeeId(employeeId);
		
		for(Salary salary : listOfsalaries) {
			if(salary.getSalaryId() == salaryId) {
				return salary;
			}
		}
		
		throw new SalaryException(statusCode_NOT_FOUND + "Employee ID "+ employeeId +" and salary ID "+ salaryId +" doesn't match in a single record.");
	}

	@Override
	public SalaryDTO deleteSalaryBySalaryId(Integer salaryId) throws SalaryException {

		Optional<Salary> Optsalary = salaryRepository.findById(salaryId);

		if(Optsalary.isPresent()) {
			Salary salary = Optsalary.get();
			//to avoid loading error
		    System.out.println ( salary.getSalarySlip() );
			 
			salaryRepository.deleteById(salaryId);
			SalaryDTO salaryDTO = new SalaryDTO(salaryId, salary.getEmployeeId(),
					salary.getBankAccountName(), salary.getBankAccountNumber(),
					salary.getBankAccountIFSC(), salary.getTransactionId(), salary.getCreditDate(),
					salary.getSalaryYear(), salary.getSalaryMonth(), salary.getSalaryTotal(), salary.getSalaryAmount(),
					salary.getSalaryCurrency(), salary.getTdsAmount(), salary.getDeductionAmount(), salary.getSalarySlip(),
					salary.getNotes());
			return salaryDTO;
		}
		else throw new SalaryException(statusCode_NOT_FOUND + "Salary Id "+salaryId+" not found.");
	}

	
	
}
