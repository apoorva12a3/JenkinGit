package com.etl.validation;

import jakarta.validation.ConstraintViolation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.etl.model.Transaction;

public class ValidationResultTransaction {
    private final List<Transaction> validTransactions;
    private final Map<Transaction, Set<ConstraintViolation<Transaction>>> erroneousTransactions;

    public List<Transaction> getValidTransactions() {
        return validTransactions;
    }

    public Map<Transaction, Set<ConstraintViolation<Transaction>>> getErroneousTransactions() {
        return erroneousTransactions;
    }

    public ValidationResultTransaction(List<Transaction> validTransactions, Map<Transaction, Set<ConstraintViolation<Transaction>>> erroneousTransactions) {
        this.validTransactions = validTransactions;
        this.erroneousTransactions = erroneousTransactions;
    }
}