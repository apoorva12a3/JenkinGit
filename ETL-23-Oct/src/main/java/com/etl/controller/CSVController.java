package com.etl.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.etl.helper.CSVHelper;
import com.etl.message.ResponseMessage;
import com.etl.model.Branch;
import com.etl.model.Customer;
import com.etl.service.BranchService;
import com.etl.service.CSVService;
import com.etl.service.CustomerService;

@Controller
@RequestMapping("/api/csv")
public class CSVController {
    @Autowired
    CSVService fileService;

    @Autowired
    CustomerService customerService;

    @Autowired
    BranchService branchService;

    @PostMapping("/upload")
    public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
        String message = "";

        if (CSVHelper.hasCSVFormat(file)) {
            try {
                fileService.saveInDB(file);
                message = "Uploaded the file successfully: " + file.getOriginalFilename();
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
            } catch (Exception e) {
                message = "Could not upload the file: " + file.getOriginalFilename() + "!" + e.getMessage();
//                System.out.println(e.getMessage());
                return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
            }
        }

        message = "Please upload a csv file!";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
    }

    @GetMapping("/getcustomers")
    public ResponseEntity<List<Customer>> getAllCustomers(){
        try {
            List<Customer> customers = customerService.getAllCustomerRecords();

            if (customers.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(customers,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getbranches")
    public ResponseEntity<List<Branch>> getAllBranch(){
        try {
            List<Branch> branches = branchService.getAllBranchRecords();

            if (branches.isEmpty()){
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(branches,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

//    @GetMapping("/getsuccesReport")
//    public ResponseEntity<?> customerRowCount(){
//        try {
//            String custCount = fileService.getReport();
//            return new ResponseEntity<>(custCount,HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

//    @GetMapping("/generatereport")
//    public ResponseEntity<?> generateReport(){
//        try {
//            long custCount = c.getCustomerCount();
//            fileService.justDisplay();
//            System.out.println(custCount);
//            return new ResponseEntity<>(custCount,HttpStatus.OK);
//        }catch (Exception e){
//            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    // to use this url do -> Authentication -> Upload -> then this, in Postman
    @GetMapping("/getCombinedReport")
    public ResponseEntity<?> getCombinedReportEndpoint() {
        try {
            String combinedReport = fileService.getCombinedReport();
            return new ResponseEntity<>(combinedReport, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
