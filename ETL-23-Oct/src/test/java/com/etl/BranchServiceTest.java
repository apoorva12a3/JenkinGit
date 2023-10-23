package com.etl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.etl.model.Branch;
import com.etl.repository.BranchRepository;
import com.etl.repository.ErrorRepository;
import com.etl.service.BranchService;
import com.etl.validation.DataValidator;
import com.etl.validation.ValidationResultBranch;
import com.etl.service.ErrorEntityService; // Import the ErrorEntityService class

@SpringBootTest
class BranchServiceTest {

    @InjectMocks
    private BranchService branchService;

    @Mock
    private BranchRepository branchRepository;

    @Mock
    private ErrorRepository errorRepository;

    @Mock
    private DataValidator dataValidator;

    @Mock
    private ErrorEntityService errorEntityService; // Mock the ErrorEntityService

    @Test
    void testSaveBranch() {
        List<Branch> branches = new ArrayList<>();
        // Add some Branch objects to the list for testing

        ValidationResultBranch validationResultBranch = new ValidationResultBranch(branches, new HashMap<>());
        when(dataValidator.checkBranchValidation(branches)).thenReturn(validationResultBranch);

        branchService.saveBranch(branches);

        verify(branchRepository, times(1)).saveAll(anyList());
        verify(errorEntityService, times(1)).saveErrorneousBranchesInDb(anyMap()); // Mock the errorEntityService
    }

    @Test
    void testGetAllBranchRecords() {
        // Mock the behavior of branchRepository.findAll() to return a list of Branch objects
        when(branchRepository.findAll()).thenReturn(new ArrayList<>());

        // Test the getAllBranchRecords method
        List<Branch> allBranchRecords = branchService.getAllBranchRecords();

        // Add assertions to verify the returned list
        assertNotNull(allBranchRecords);
        assertEquals(0, allBranchRecords.size());
    }

    @Test
    void testGetBranchCount() {
        // Mock the behavior of branchRepository.count() to return a specific count
        when(branchRepository.count()).thenReturn(5L);

        // Test the getBranchCount method
        long branchCount = branchService.getBranchCount();

        // Add assertions to verify the returned count
        assertEquals(5L, branchCount);
    }

    @Test
    void testGetBranchCountForReport() {
        // Create a list of Branch objects for testing
        List<Branch> branches = new ArrayList<>();
        // Add some Branch objects to the list

        // Mock the DataValidator and its behavior
        ValidationResultBranch validationResultBranch = new ValidationResultBranch(branches, new HashMap<>());
        when(dataValidator.checkBranchValidation(branches)).thenReturn(validationResultBranch);

        // Test the getBranchCountForReport method
        String report = branchService.getBranchCountForReport(branches);

        // Add assertions to verify the generated report
        assertNotNull(report);
        assertTrue(report.contains("Data values fetched successfully from Branch"));
    }
}