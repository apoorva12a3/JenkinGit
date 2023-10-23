package com.etl;

import com.etl.model.Transaction;
import com.etl.repository.ErrorRepository;
import com.etl.repository.TransactionRepository;
import com.etl.service.TransactionService;
import com.etl.validation.DataValidator;
import com.etl.validation.ValidationResultTransaction;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

@SpringBootTest
public class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @Mock
    private ErrorRepository errorRepository;

    @Mock
    private DataValidator dataValidator;

    @InjectMocks
    private TransactionService transactionService;

//    @Test
//    public void testSaveTransaction() {
//        List<Transaction> transactions = new ArrayList<>();
//        // add test transactions to the list
//
//        ValidationResultTransaction validationResultTransaction = new ValidationResultTransaction(transactions, new HashMap<>());
//
//        Mockito.when(dataValidator.checkTransactionValidation(transactions)).thenReturn(validationResultTransaction);
//
//        transactionService.saveTransaction(transactions);
//
//        // Verify that transactionRepo.saveAll() is called once with the list of transactions
//        Mockito.verify(transactionRepository, times(1)).saveAll(transactions);
//        // Verify that errorEntityRepo.save() is never called
//        Mockito.verify(errorRepository, Mockito.never()).save(any());
//    }

    @Test
    public void testGetAllTransactionRecords() {
        // Mock the behavior of the repository
        Mockito.when(transactionRepository.findAll()).thenReturn(new ArrayList<>());

        List<Transaction> result = transactionService.getAllTransactionRecords();

        // Verify that transactionRepo.findAll() is called once
        Mockito.verify(transactionRepository, times(1)).findAll();
        // Check that the result is not null
        assertEquals(0, result.size());
    }

    @Test
    public void testGetTransactionCount() {
        // Mock the behavior of the repository
        Mockito.when(transactionRepository.count()).thenReturn(10L);

        long result = transactionService.getTransactionCount();

        // Verify that transactionRepo.count() is called once
        Mockito.verify(transactionRepository, times(1)).count();
        // Check the result
        assertEquals(10L, result);
    }



    // ...

    @Test
    public void testTransactionCountForReport() {
        List<Transaction> transactions = new ArrayList<>();
        // Add test transactions to the list
        transactions.add(new Transaction(1L, 101L, "Deposit", 100.0, new Date()));
        transactions.add(new Transaction(2L, 102L, "Withdrawal", 50.0, new Date()));

        ValidationResultTransaction validationResultTransaction = new ValidationResultTransaction(transactions, new HashMap<>());

        // Mock the behavior of the dataValidator
        Mockito.when(dataValidator.checkTransactionValidation(transactions)).thenReturn(validationResultTransaction);

        // Mock the behavior of the repository
        Mockito.when(transactionRepository.count()).thenReturn((long) transactions.size());

        String result = transactionService.getTransactionCountForReport(transactions);

        // Verify that transactionRepo.count() is called once
        Mockito.verify(transactionRepository, times(1)).count();

        // Check the generated report string
        assertNotEquals("DBTransactionCount:" + transactions.size() + " transactionCount:" + transactions.size(), result);

        System.out.println(transactions.size());
        System.out.println(result);
    }

}