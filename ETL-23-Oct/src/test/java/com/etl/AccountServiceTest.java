package com.etl;

import com.etl.model.Account;
import com.etl.repository.AccountRepository;
import com.etl.repository.ErrorRepository;
import com.etl.service.AccountService;
import com.etl.service.ErrorEntityService;
import com.etl.validation.DataValidator;
import com.etl.validation.ValidationResultAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

public class AccountServiceTest {

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private ErrorRepository errorRepository;

    @Mock
    private DataValidator dataValidator;

    @Mock
    private ErrorEntityService errorEntityService;

    @InjectMocks
    private AccountService accountService;

    private List<Account> accounts;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // Initialize mocks

        accounts = new ArrayList<>();
        // Add test accounts to the list if necessary
    }

    @Test
    public void testSaveAccount() {
        ValidationResultAccount validationResultAccount = new ValidationResultAccount(new ArrayList<>(), new HashMap<>());
        Mockito.when(dataValidator.checkaccountValidation(accounts)).thenReturn(validationResultAccount);

        accountService.saveAccount(accounts);

        Mockito.verify(accountRepository, times(1)).saveAll(Mockito.anyList());
        Mockito.verify(errorEntityService, times(1)).saveErrorneousAccountsInDB(Mockito.anyMap());
    }

    @Test
    public void testGetAllBranchRecords() {
        Mockito.when(accountRepository.findAll()).thenReturn(new ArrayList<>());

        List<Account> result = accountService.getAllBranchRecords();

        assertEquals(0, result.size());
    }

    @Test
    public void testGetAccountCount() {
        Mockito.when(accountRepository.count()).thenReturn(10L);

        long result = accountService.getAccountCount();

        assertEquals(10L, result);
    }

    @Test
    public void testGetAllAccountRecords() {
        Mockito.when(accountRepository.findAll()).thenReturn(new ArrayList<>());

        List<Account> result = accountService.getAllAccountRecords();

        assertEquals(0, result.size());
    }

    @Test
    public void testGetAccountCountForReport() {
        ValidationResultAccount validationResultAccount = new ValidationResultAccount(new ArrayList<>(), new HashMap<>());
        Mockito.when(dataValidator.checkaccountValidation(accounts)).thenReturn(validationResultAccount);

        Mockito.when(accountRepository.count()).thenReturn((long) accounts.size());

        String result = accountService.getAccountCountForReport(accounts);

        assertEquals("Data values fetched successfully from Account : 0 | Total data provided :0 | Violations : 0 | Date: 23-10-2023", result);
    }
}
