package com.etl.controller;

import com.etl.model.Account;
import com.etl.model.Loan;
import com.etl.model.Branch;
import com.etl.model.Transaction;
import com.etl.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import com.etl.model.Customer;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.List;

@Controller
@RequestMapping("/report")
public class ReportController {
    public ReportController(ReportService reportService, CustomerService customerService) {
        this.reportService = reportService;
        this.customerService = customerService;
    }

    @Autowired
    private ReportService reportService;

    @Autowired
    private CSVService csvService;
    @Autowired
    private CustomerService customerService;
    @Autowired
    private AccountService accountService;
    @Autowired
    private BranchService branchService;
    @Autowired
    private LoanService loanService;
    @Autowired
    private TransactionService transactionService;
    @Autowired
    private EmailService emailService;

    @GetMapping("/generateAndSendReport")
    public ResponseEntity<?> generateAndSendReport() {
        List<Customer> customer1 = customerService.getAllCustomerRecords();
        List<Account> account1 = accountService.getAllAccountRecords();
        List<Loan> loan1 = loanService.getAllLoanRecords();
        List<Branch> branch1 = branchService.getAllBranchRecords();
        List<Transaction> transaction1 = transactionService.getAllTransactionRecords();

        try {
            // Generate the PDF error report
            ByteArrayInputStream pdfReport = reportService.generateErrorReport();

            // Fetch the dynamic data from respective services
            String customerReport = customerService.getCustomerCountForReport(customer1);
            String accountReport = accountService.getAccountCountForReport(account1);
            String branchReport = branchService.getBranchCountForReport(branch1);
            String loanReport = loanService.getLoanCountForReport(loan1);
            String transactionReport = transactionService.getTransactionCountForReport(transaction1);

            // Check if customerReport is null
            if (customerReport != null) {
                // Combine the PDF report and dynamic data
                ByteArrayOutputStream combinedReport = new ByteArrayOutputStream();
                combinedReport.write(pdfReport.readAllBytes());
                combinedReport.write(System.lineSeparator().getBytes());
                combinedReport.write(customerReport.getBytes());
                combinedReport.write(System.lineSeparator().getBytes());
                combinedReport.write(accountReport.getBytes());
                combinedReport.write(System.lineSeparator().getBytes());
                combinedReport.write(branchReport.getBytes());
                combinedReport.write(System.lineSeparator().getBytes());
                combinedReport.write(loanReport.getBytes());
                combinedReport.write(System.lineSeparator().getBytes());
                combinedReport.write(transactionReport.getBytes());

                // Send the email with the combined report as an attachment
                emailService.sendEmailWithAttachment(
                        "madhubala4065@gmail.com", // Replace with the recipient's email address
                        "Batch Report",
                        "Please find the attached the Batch Report.",
                        combinedReport.toByteArray(),
                        "Batch_report.pdf"
                );

                // Set response headers for downloading the combined PDF report
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_PDF);
                headers.setContentDispositionFormData("attachment", "Batch_report.pdf");

                return new ResponseEntity<>(combinedReport.toByteArray(), headers, HttpStatus.OK);
            } else {
                // Handle the case where customerReport is null (e.g., return an error response)
                return new ResponseEntity<>("Customer report is null", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
