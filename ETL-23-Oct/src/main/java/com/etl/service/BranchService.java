package com.etl.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.etl.model.Branch;
import com.etl.model.ErrorEntity;
import com.etl.repository.BranchRepository;
import com.etl.repository.ErrorRepository;
import com.etl.validation.DataValidator;
import com.etl.validation.ValidationResultBranch;

import jakarta.validation.ConstraintViolation;

@Service
public class BranchService {

    @Autowired
    BranchRepository branchRepo;

    @Autowired
    private ErrorRepository errorEntityRepo;

    @Autowired
    DataValidator dataValidator;

    @Autowired
    ErrorEntityService errorEntityService;

    public void saveBranch(List<Branch> branches) {

        ValidationResultBranch validationResultBranch = dataValidator.checkBranchValidation(branches); // Use BranchValidationResult

        branchRepo.saveAll(validationResultBranch.getValidBranches()); // Use validationResultBranch

        Map<Branch, Set<ConstraintViolation<Branch>>> erroneousBranches = validationResultBranch.getErroneousBranches(); // Use validationResultBranch
        errorEntityService.saveErrorneousBranchesInDb(erroneousBranches);

    }

    public List<Branch> getAllBranchRecords() {
        return branchRepo.findAll();
    }

    public long getBranchCount() {
        return branchRepo.count();
    }


//    public String getBranchCountForReport(List<Branch> branchList) {
//        long dbBranchCount = getBranchCount();
//        long branchCount = branchList.size();
//        if (dbBranchCount == branchCount) {
//            //generate success report
//            LocalDate currentDate = LocalDate.now();
//            System.out.println("Date: " + currentDate + " | DBBranchCount:" + dbBranchCount + " BranchCount:" + branchCount);
//        }
//        LocalDate currentDate = LocalDate.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
//        String formattedDate = currentDate.format(formatter);
//        return "Data values fetched successfully from Branch : " + dbBranchCount + " | Total data provided :" + branchCount
//                + " | Violations : " + (branchCount - dbBranchCount) + " | Date: " + formattedDate;
//    }

    public String getBranchCountForReport(List<Branch> branchList) {
        long dbBranchCount = getBranchCount();
        long branchCount = branchList.size();

        ValidationResultBranch validationResult1 = dataValidator.checkBranchValidation(branchList);
        Map<Branch, Set<ConstraintViolation<Branch>>> erroneousBranches = validationResult1.getErroneousBranches();
        long violations = erroneousBranches.size();

        long successfulDataCount = branchCount - violations;

        if (dbBranchCount == branchCount) {
            // generate success report
            LocalDate currentDate = LocalDate.now();
            System.out.println("Date: " + currentDate + " | DBBranchCount:" + dbBranchCount + " BranchCount:" + branchCount);
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        return "Data values fetched successfully from Branch : " + successfulDataCount
                + " | Total data provided :" + branchCount
                + " | Violations : " + violations
                + " | Date: " + formattedDate;
    }
}
