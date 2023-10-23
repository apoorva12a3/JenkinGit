package com.etl.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.model.Customer;
import com.etl.model.ErrorEntity;
import com.etl.repository.CustomerRepository;
import com.etl.repository.ErrorRepository;
import com.etl.validation.DataValidator;
import com.etl.validation.ValidationResultCustomer;

import jakarta.validation.ConstraintViolation;

@Service
public class CustomerService {

	@Autowired
	CustomerRepository customerRepo;

	@Autowired
	private ErrorRepository errorEntityRepo;

	@Autowired
	DataValidator dataValidator;

	@Autowired
	ErrorEntityService errorEntityService;

	public void saveCustomer(List<Customer> customer1) {
		// customer1 = CSVHelper.customer_func(file.getInputStream());

		ValidationResultCustomer validationResultCustomer = dataValidator.checkCustomerValidation(customer1);

		customerRepo.saveAll(validationResultCustomer.getValidCustomers());

		Map<Customer, Set<ConstraintViolation<Customer>>> erroneousCustomers = validationResultCustomer.getErroneousCustomers();
		errorEntityService.saveErrorneousCustomersInDB(erroneousCustomers);

	}

	public List<Customer> getAllCustomerRecords(){
		return customerRepo.findAll();
	}

	public long getCustomerCount() {
		return customerRepo.count();
	}


//	public String getCustomerCountForReport(List<Customer> customer1) {
//		long dbCustomerCount = getCustomerCount();
//		long customerCount = customer1.size();
//		if (dbCustomerCount == customerCount) {
//			//generate success report
//			System.out.println("Data values fetched successfully from Customer : " + dbCustomerCount + " | Total data provided : " + customerCount
//					+ " | Violations : " + (customerCount - dbCustomerCount));
//		}
//		LocalDate currentDate = LocalDate.now();
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//		String formattedDate = currentDate.format(formatter);
//		return "Data values fetched successfully from Customer : " + dbCustomerCount + " | Total data provided :" + customerCount
//				+ " | Violations : " + (customerCount - dbCustomerCount) + " | Date: " + formattedDate;
//	}


	public String getCustomerCountForReport(List<Customer> customerList) {
		long dbCustomerCount = getCustomerCount();
		long customerCount = customerList.size();

		ValidationResultCustomer validationResult = dataValidator.checkCustomerValidation(customerList);
		Map<Customer, Set<ConstraintViolation<Customer>>> erroneousCustomers = validationResult.getErroneousCustomers();
		long violations = erroneousCustomers.size();
		long successfulDataCount = customerCount - violations;

		if (dbCustomerCount == customerCount) {
			// generate success report
			LocalDate currentDate = LocalDate.now();
			System.out.println("Date: " + currentDate + " | DBCustomerCount:" + dbCustomerCount + " CustomerCount:" + customerCount);
		}

		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
		String formattedDate = currentDate.format(formatter);
		return "Data values fetched successfully from Customer : " + successfulDataCount
				+ " | Total data provided :" + customerCount
				+ " | Violations : " + violations
				+ " | Date: " + formattedDate;
	}
}
