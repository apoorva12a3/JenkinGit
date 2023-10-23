package com.etl.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.model.Loan;
import com.etl.repository.ErrorRepository;
import com.etl.repository.LoanRepository;
import com.etl.validation.DataValidator;
import com.etl.validation.ValidationResultLoan;

import jakarta.validation.ConstraintViolation;

@Service
public class LoanService {

    @Autowired
    LoanRepository loanRepo;

    @Autowired
    private ErrorRepository errorEntityRepo;

    @Autowired
    DataValidator dataValidator;

    @Autowired
    ErrorEntityService errorEntityService;

    public void saveLoan(List<Loan> loans) {
        ValidationResultLoan validationResultLoan = dataValidator.checkLoanValidation(loans);

        // Continue with saving valid loans...
        List<Loan> validLoans = validationResultLoan.getValidLoans();
        loanRepo.saveAll(validLoans);

        // Handle erroneous loans and violations as needed
        Map<Loan, Set<ConstraintViolation<Loan>>> erroneousLoans = validationResultLoan.getErroneousLoans();
        errorEntityService.saveErrorneousLoansInDB(erroneousLoans);

    }
    public List<Loan> getAllLoanRecords() {
        return loanRepo.findAll();
    }

    public long getLoanCount() {
        return loanRepo.count();
    }
//    public String getLoanCountForReport(List<Loan> loanList) {
//        long dbLoanCount = getLoanCount();
//        long loanCount = loanList.size();
//        if (dbLoanCount == loanCount) {
//            //generate success report
//            LocalDate currentDate = LocalDate.now();
//            System.out.println("Date: " + currentDate + " | DBLoanCount:" + dbLoanCount + " LoanCount:" + loanCount);
//        }
//        LocalDate currentDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        String formattedDate = currentDate.format(formatter);
//        return "Data values fetched successfully from Loan : " + dbLoanCount + " | Total data provided :" + loanCount
//                + " | Violations : " + (loanCount - dbLoanCount) + " | Date: " + formattedDate;
//    }

    public String getLoanCountForReport(List<Loan> loanList) {
        long dbLoanCount = getLoanCount();
        long loanCount = loanList.size();

        ValidationResultLoan loanValidationResult = dataValidator.checkLoanValidation(loanList);
        Map<Loan, Set<ConstraintViolation<Loan>>> erroneousLoans = loanValidationResult.getErroneousLoans();
        long violations = erroneousLoans.size();
        long successfulDataCount = loanCount - violations;

        if (dbLoanCount == loanCount) {
            // generate success report
            LocalDate currentDate = LocalDate.now();
            System.out.println("Date: " + currentDate + " | DBLoanCount:" + dbLoanCount + " LoanCount:" + loanCount);
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        return "Data values fetched successfully from Loan : " + successfulDataCount
                + " | Total data provided :" + loanCount
                + " | Violations : " + violations
                + " | Date: " + formattedDate;
    }
}

