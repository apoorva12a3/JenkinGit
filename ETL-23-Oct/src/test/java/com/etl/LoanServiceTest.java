package com.etl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.etl.model.Loan;
import com.etl.repository.ErrorRepository;
import com.etl.repository.LoanRepository;
import com.etl.validation.DataValidator;
import com.etl.validation.ValidationResultLoan;
import com.etl.service.LoanService;
import com.etl.service.ErrorEntityService;
import jakarta.validation.ConstraintViolation;

import java.util.*;

@SpringBootTest
class LoanServiceTest {

    @InjectMocks
    private LoanService loanService;

    @Mock
    private LoanRepository loanRepository;

    @Mock
    private ErrorRepository errorRepository;

    @Mock
    private DataValidator dataValidator;

    @Mock
    private ErrorEntityService errorEntityService;

    @Test
    void testSaveLoan() {
        // Create a list of Loan objects for testing
        List<Loan> loans = new ArrayList<>();
        // Add some Loan objects to the list

        // Mock the DataValidator and its behavior
        ValidationResultLoan validationResultLoan = new ValidationResultLoan(loans, new HashMap<>());
        when(dataValidator.checkLoanValidation(loans)).thenReturn(validationResultLoan);

        // Test the saveLoan method
        loanService.saveLoan(loans);

        // Add assertions for the behavior of the loanRepository and errorEntityService
        verify(loanRepository, times(1)).saveAll(anyList());
        verify(errorEntityService, times(1)).saveErrorneousLoansInDB(anyMap());
    }

    @Test
    void testGetAllLoanRecords() {
        // Mock the behavior of loanRepository.findAll() to return a list of Loan objects
        when(loanRepository.findAll()).thenReturn(new ArrayList<>());

        // Test the getAllLoanRecords method
        List<Loan> allLoanRecords = loanService.getAllLoanRecords();

        // Add assertions to verify the returned list
        assertNotNull(allLoanRecords);
        assertEquals(0, allLoanRecords.size());
    }

    @Test
    void testGetLoanCount() {
        // Mock the behavior of loanRepository.count() to return a specific count
        when(loanRepository.count()).thenReturn(10L);

        // Test the getLoanCount method
        long loanCount = loanService.getLoanCount();

        // Add assertions to verify the returned count
        assertEquals(10L, loanCount);
    }

    @Test
    void testGetLoanCountForReport() {
        // Create a list of Loan objects for testing
        List<Loan> loans = new ArrayList<>();
        // Add some Loan objects to the list

        // Mock the DataValidator and its behavior
        ValidationResultLoan validationResultLoan = new ValidationResultLoan(loans, new HashMap<>());
        when(dataValidator.checkLoanValidation(loans)).thenReturn(validationResultLoan);

        // Test the getLoanCountForReport method
        String report = loanService.getLoanCountForReport(loans);

        // Add assertions to verify the generated report
        assertNotNull(report);
        assertTrue(report.contains("Data values fetched successfully from Loan"));
    }
}