package com.etl.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.etl.model.Branch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.model.Account;
import com.etl.model.ErrorEntity;
import com.etl.repository.AccountRepository;
import com.etl.repository.ErrorRepository;
import com.etl.validation.DataValidator;
import com.etl.validation.ValidationResultAccount;

import jakarta.validation.ConstraintViolation;

@Service
public class AccountService {
    @Autowired
	AccountRepository accountRepo;
	
	@Autowired
	private ErrorRepository errorEntityRepo;
	
	@Autowired
    DataValidator dataValidator;

    @Autowired
    ErrorEntityService errorEntityService;
	
	public void saveAccount(List<Account> accounts) {
		
		ValidationResultAccount validationResultAccount = dataValidator.checkaccountValidation(accounts); // Use BranchValidationResult

        accountRepo.saveAll(validationResultAccount.getvalidAccounts()); // Use validationResult1

        Map<Account, Set<ConstraintViolation<Account>>> erroneousAccounts = validationResultAccount.geterroneousAccounts(); // Use validationResult1
        errorEntityService.saveErrorneousAccountsInDB(erroneousAccounts);

	}
    public List<Account> getAllBranchRecords() {
        return accountRepo.findAll();
    }

    public long getAccountCount() {
        return accountRepo.count();
    }

	public List<Account> getAllAccountRecords() {
        return accountRepo.findAll();
    }


    public String getAccountCountForReport(List<Account> accountList) {
        long dbAccountCount = getAccountCount();
        long accountCount = accountList.size();

        ValidationResultAccount validationResultAccount = dataValidator.checkaccountValidation(accountList);
        Map<Account, Set<ConstraintViolation<Account>>> erroneousAccounts = validationResultAccount.geterroneousAccounts();
        long violations = erroneousAccounts.size();

        long successfulDataCount = accountCount - violations;

        if (dbAccountCount == accountCount) {
            // generate success report
            LocalDate currentDate = LocalDate.now();
            System.out.println("Date: " + currentDate + " | DBAccountCount:" + dbAccountCount + " AccountCount:" + accountCount);
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        return "Data values fetched successfully from Account : " + successfulDataCount
                + " | Total data provided :" + accountCount
                + " | Violations : " + violations
                + " | Date: " + formattedDate;
    }
}
