package com.etl.validation;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.etl.model.Account;
import com.etl.model.Branch;
import com.etl.model.Customer;
import com.etl.model.ErrorEntity;
import com.etl.model.Loan;
import com.etl.model.Transaction;
import com.etl.repository.ErrorRepository;

import java.util.*;

@Component
public class DataValidator {

    private final Validator validator;

    //@Autowired
    public DataValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        this.validator = factory.getValidator();
    }

    @Autowired
    private ErrorRepository errorEntityRepo; // You need to create this repository.

    public ValidationResultCustomer checkCustomerValidation(List<Customer> customer1) {
        List<Customer> validCustomers = new ArrayList<>();
        Map<Customer, Set<ConstraintViolation<Customer>>> erroneousCustomers = new HashMap<>();

        for (Customer cust : customer1) {
            Set<ConstraintViolation<Customer>> violations = validator.validate(cust);

            if (!violations.isEmpty()) {
                // Handle validation errors
                erroneousCustomers.put(cust, violations);
            } else {
                // Your logic if validation passes
                validCustomers.add(cust);
//                System.out.println("All good for " + cust.getFirstName() + " " + cust.getEmailID());
            }
        }

        return new ValidationResultCustomer(validCustomers, erroneousCustomers);
    }

    //------------LOAN--------
    public ValidationResultLoan checkLoanValidation(List<Loan> loanList) {
        List<Loan> validLoans = new ArrayList<>();
        Map<Loan, Set<ConstraintViolation<Loan>>> erroneousLoans = new HashMap<>();

        for (Loan loan : loanList) {
            Set<ConstraintViolation<Loan>> violations = validator.validate(loan);

            if (!violations.isEmpty()) {
                erroneousLoans.put(loan, violations);
//              
            } else {
                validLoans.add(loan);
            }
        }

        return new ValidationResultLoan(validLoans, erroneousLoans);
    }
    //---------------account-------------------
    public ValidationResultAccount checkaccountValidation(List<Account> accountList) {
        List<Account> validAccounts = new ArrayList<>();
        Map<Account, Set<ConstraintViolation<Account>>> erroneousaccounts = new HashMap<>();

        for (Account acc : accountList) {
            Set<ConstraintViolation<Account>> violations = validator.validate(acc);

            if (!violations.isEmpty()) {
                erroneousaccounts.put(acc, violations);
            } else {
                validAccounts.add(acc);
            }
        }

        return new ValidationResultAccount(validAccounts, erroneousaccounts);
    }
    //-----------------branch------------
    public ValidationResultBranch checkBranchValidation(List<Branch> branches) {
        List<Branch> validBranches = new ArrayList<>();
        Map<Branch, Set<ConstraintViolation<Branch>>> erroneousBranches = new HashMap<>();

        for (Branch branch : branches) {
            Set<ConstraintViolation<Branch>> violations = validator.validate(branch);

            if (!violations.isEmpty()) {
                // Handle validation errors
                erroneousBranches.put(branch, violations);
            } else {
                // Your logic if validation passes
                validBranches.add(branch);
//                System.out.println("All good for branch: " + branch.getBranchLocation());
            }
        }

        return new ValidationResultBranch(validBranches, erroneousBranches);
    }
    //-----------------------Transactions-------------
    public ValidationResultTransaction checkTransactionValidation(List<Transaction> transaction1){
        List<Transaction> validTransactions = new ArrayList<>();
        Map<Transaction, Set<ConstraintViolation<Transaction>>> erroneousTransactions = new HashMap<>();

        for (Transaction trs:transaction1){
            Set<ConstraintViolation<Transaction>> violations = validator.validate(trs);

            if(!violations.isEmpty()){
                // Handle validation errors
                erroneousTransactions.put(trs,violations);
            }
            else {
                // Your logic if validation passes
                validTransactions.add(trs);
//                System.out.println("All good for " + trs.getFirstName() + " " + trs.getEmailID());
            }
        }
        return new ValidationResultTransaction(validTransactions, erroneousTransactions);
    }
}
