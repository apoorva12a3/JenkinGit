package com.etl.validation;

import jakarta.validation.ConstraintViolation;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.etl.model.Branch;

public class ValidationResultBranch {
    private final List<Branch> validBranches;
    private final Map<Branch, Set<ConstraintViolation<Branch>>> erroneousBranches;

    public ValidationResultBranch(List<Branch> validBranches, Map<Branch, Set<ConstraintViolation<Branch>>> erroneousBranches) {
        this.validBranches = validBranches;
        this.erroneousBranches = erroneousBranches;
    }

    public List<Branch> getValidBranches() {
        return validBranches;
    }

    public Map<Branch, Set<ConstraintViolation<Branch>>> getErroneousBranches() {
        return erroneousBranches;
    }
}