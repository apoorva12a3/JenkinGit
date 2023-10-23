package com.etl.service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.model.Account;
import com.etl.model.Branch;
import com.etl.model.Customer;
import com.etl.model.ErrorEntity;
import com.etl.model.Loan;
import com.etl.model.Transaction;
import com.etl.repository.ErrorRepository;

import jakarta.validation.ConstraintViolation;

@Service
public class ErrorEntityService {

    @Autowired
    private ErrorRepository errorEntityRepo;

    public void saveErrorneousCustomersInDB(Map<Customer, Set<ConstraintViolation<Customer>>> erroneousCustomers) {

        for (Map.Entry<Customer, Set<ConstraintViolation<Customer>>> entry : erroneousCustomers.entrySet()) {
            Customer erroneousCustomer = entry.getKey();
            Set<ConstraintViolation<Customer>> violations = entry.getValue();

            System.out.println("Handling erroneous customer: " + erroneousCustomer.toString());
            System.out.println("Violations: " + violations);
            // Handle the erroneous customer and violations as needed
            if (!violations.isEmpty()) {
                for (ConstraintViolation<Customer> violation : violations) {
                    ErrorEntity errorEntity = new ErrorEntity();
                    errorEntity.setEntityName("Customer");
                    errorEntity.setErrorMessage("Validation Exception for customerID: " + entry.getKey().getCustomerID()
                            + ": " + violation.getMessage());
                    errorEntity.setTimestamp(LocalDateTime.now());
                    errorEntityRepo.save(errorEntity);
                }
            }
        }
    }

    public void saveErrorneousBranchesInDb(Map<Branch, Set<ConstraintViolation<Branch>>> erroneousBranches) {

        for (Map.Entry<Branch, Set<ConstraintViolation<Branch>>> entry : erroneousBranches.entrySet()) {
            Branch erroneousBranch = entry.getKey();
            Set<ConstraintViolation<Branch>> violations = entry.getValue();

            System.out.println("Handling erroneous branch: " + erroneousBranch.toString());
            System.out.println("Violations: " + violations);
            // Handle the erroneous branch and violations as needed
            if (!violations.isEmpty()) {
                // Handle validation errors
                for (ConstraintViolation<Branch> violation : violations) {
                    ErrorEntity errorEntity = new ErrorEntity();
                    errorEntity.setEntityName("Branch");
                    errorEntity.setErrorMessage("Validation Exception for Branch entity with BranchID "
                            + entry.getKey().getBranchID() + ": " + violation.getMessage());
                    errorEntity.setTimestamp(LocalDateTime.now());
                    errorEntityRepo.save(errorEntity);
                }
            }
        }
    }

    public void saveErrorneousAccountsInDB(Map<Account, Set<ConstraintViolation<Account>>> erroneousAccounts) {

        for (Map.Entry<Account, Set<ConstraintViolation<Account>>> entry : erroneousAccounts.entrySet()) {
            Account erroneousAccount = entry.getKey();
            Set<ConstraintViolation<Account>> violations = entry.getValue();

            System.out.println("Handling erroneous account: " + erroneousAccount.toString());
            System.out.println("Violations: " + violations);
            // Handle the erroneous branch and violations as needed
            if (!violations.isEmpty()) {

                for (ConstraintViolation<Account> violation : violations) {
                    ErrorEntity errorEntity = new ErrorEntity();
                    errorEntity.setEntityName("Account");
                    errorEntity.setErrorMessage("Validation Exception for account entity with account ID " + entry.getKey().getAccountID()  + ": " + violation.getMessage());
                    errorEntity.setTimestamp(LocalDateTime.now());
                    errorEntityRepo.save(errorEntity);
                }
            }
        }
    }

    public void saveErrorneousLoansInDB(Map<Loan, Set<ConstraintViolation<Loan>>> erroneousLoans) {

        for (Map.Entry<Loan, Set<ConstraintViolation<Loan>>> entry : erroneousLoans.entrySet()) {
            Loan erroneousLoan = entry.getKey();
            Set<ConstraintViolation<Loan>> violations = entry.getValue();

            System.out.println("Handling erroneous loan: " + erroneousLoan.toString());
            System.out.println("Violations: " + violations);
            // Handle the erroneous loan and violations as needed
            if (!violations.isEmpty()) {
                for (ConstraintViolation<Loan> violation : violations) {
                    ErrorEntity errorEntity = new ErrorEntity();
                    errorEntity.setEntityName("Loan");
                    errorEntity.setErrorMessage("Validation Exception for Loan entity with loan ID " + entry.getKey().getLoanID() + "And with CustomerID " + entry.getKey().getCustomerID() + ": " + violation.getMessage());
                    errorEntity.setTimestamp(LocalDateTime.now());
                    errorEntityRepo.save(errorEntity);
                }
            }
        }
    }

    public void saveErrorneousTransactionsInDB(Map<Transaction, Set<ConstraintViolation<Transaction>>> erroneousTransactions) {
        for (Map.Entry<Transaction, Set<ConstraintViolation<Transaction>>> entry : erroneousTransactions.entrySet()) {
            Transaction erroneousTransaction = entry.getKey();
            Set<ConstraintViolation<Transaction>> violations = entry.getValue();

            // showing in terminal
            System.out.println("Handling erroneous transaction: " + erroneousTransaction.toString());
            System.out.println("Violations: " + violations );
            // Handle the erroneous customer and violations as needed

            if(!violations.isEmpty()){
                // Handle validation errors
                for (ConstraintViolation<Transaction> violation : violations) {
                    ErrorEntity errorEntity = new ErrorEntity();
                    errorEntity.setEntityName("trs");
                    errorEntity.setErrorMessage("Validation Exception for transaction entity with transactionID " + entry.getKey().getTransactionID() +"with accountID"+ entry.getKey().getAccountID()+ ": " + violation.getMessage());
                    errorEntity.setTimestamp(LocalDateTime.now());
                    errorEntityRepo.save(errorEntity);
                }
            }
        }
    }
}
