package com.etl.validation;

import jakarta.validation.ConstraintViolation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.etl.model.Loan;

public class ValidationResultLoan {
    private final List<Loan> validLoans;
    private final Map<Loan, Set<ConstraintViolation<Loan>>> erroneousLoans;

    public ValidationResultLoan(List<Loan> validLoans, Map<Loan, Set<ConstraintViolation<Loan>>> erroneousLoans) {
        this.validLoans = validLoans;
        this.erroneousLoans = erroneousLoans;
    }

    public List<Loan> getValidLoans() {
        return validLoans;
    }

    public Map<Loan, Set<ConstraintViolation<Loan>>> getErroneousLoans() {
        return erroneousLoans;
    }
}