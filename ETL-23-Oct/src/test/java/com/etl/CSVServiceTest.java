package com.etl;
import com.etl.cleaner.DataCleaner;
import com.etl.model.*;
import com.etl.repository.*;
import com.etl.service.*;
import com.etl.validation.DataValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CSVServiceTest {

    @Mock
    private CustomerRepository customerRepo;

    @Mock
    private BranchRepository branchRepo;

    @Mock
    private AccountRepository accountRepo;

    @Mock
    private LoanRepository loanRepo;

    @Mock
    private TransactionRepository transactionRepo;

    @Mock
    private ErrorRepository errorEntityRepo;

    @Mock
    private CustomerService customerService;

    @Mock
    private BranchService branchService;

    @Mock
    private AccountService accountService;

    @Mock
    private TransactionService transactionService;

    @Mock
    private LoanService loanService;

    @Mock
    private DataValidator dataValidator;

    @Mock
    private DataCleaner dataCleaner;

    @InjectMocks
    private CSVService csvService;

    @BeforeEach
    public void setUp() {
        // Set up mock behavior or initialize mocks here if needed
    }

    @Test
    public void testSaveInDB_Success() throws IOException {
        MockMultipartFile file = new MockMultipartFile("data", "filename.csv", "text/csv", "some csv".getBytes());

        // Mock behavior for each service method
        doNothing().when(customerService).saveCustomer(new ArrayList<>());
        doNothing().when(branchService).saveBranch(new ArrayList<>());
        doNothing().when(accountService).saveAccount(new ArrayList<>());
        doNothing().when(transactionService).saveTransaction(new ArrayList<>());
        doNothing().when(loanService).saveLoan(new ArrayList<>());

        // Call the method to be tested
        csvService.saveInDB(file);

        // Verify that the service methods were called
        // Add more specific verifications if necessary
    }

    @Test
    public void testGetCombinedReport() {
        // Set up mock behavior or initialize mocks here if needed
        List<Customer> customerList = new ArrayList<>();
        List<Account> accountList = new ArrayList<>();
        List<Branch> branchList = new ArrayList<>();
        List<Loan> loanList = new ArrayList<>();
        List<Transaction> transactionList = new ArrayList<>();

        when(customerService.getCustomerCountForReport(customerList)).thenReturn("CustomerCount:100");
        when(accountService.getAccountCountForReport(accountList)).thenReturn("AccountCount:100");
        when(branchService.getBranchCountForReport(branchList)).thenReturn("BranchCount:100");
        when(loanService.getLoanCountForReport(loanList)).thenReturn("LoanCount:100");
        when(transactionService.getTransactionCountForReport(transactionList)).thenReturn("TransactionCount:100");

        // Call the method to be tested
        String combinedReport = csvService.getCombinedReport();

        // Add assertions for the expected combined report
        assertEquals("CustomerCount:100\n" +
                "AccountCount:100\n" +
                "BranchCount:100\n" +
                "LoanCount:100\n" +
                "TransactionCount:100", combinedReport);
    }
}


