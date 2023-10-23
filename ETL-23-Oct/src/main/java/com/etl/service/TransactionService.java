package com.etl.service;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.etl.model.Transaction;
import com.etl.repository.ErrorRepository;
import com.etl.repository.TransactionRepository;
import com.etl.validation.DataValidator;
import com.etl.validation.ValidationResultTransaction;
import jakarta.validation.ConstraintViolation;

@Service
public class TransactionService {

    @Autowired
    TransactionRepository transactionRepo;

    @Autowired
    private ErrorRepository errorEntityRepo;

    @Autowired
    DataValidator dataValidator;

    @Autowired
    ErrorEntityService errorEntityService;

    public void saveTransaction(List<Transaction> transactions) {

        ValidationResultTransaction validationResultTransaction = dataValidator.checkTransactionValidation(transactions);

        transactionRepo.saveAll(validationResultTransaction.getValidTransactions());

        Map<Transaction, Set<ConstraintViolation<Transaction>>> erroneousTransactions = validationResultTransaction.getErroneousTransactions();
        errorEntityService.saveErrorneousTransactionsInDB(erroneousTransactions);

    }

    public List<Transaction> getAllTransactionRecords() {
        return transactionRepo.findAll();
    }

    public long getTransactionCount() {
        return transactionRepo.count();
    }


    public String getTransactionCountForReport(List<Transaction> transactionList) {
        long dbTransactionCount = getTransactionCount();
        long transactionCount = transactionList.size();

        ValidationResultTransaction validationResultTransaction = dataValidator.checkTransactionValidation(transactionList);
        Map<Transaction, Set<ConstraintViolation<Transaction>>> erroneousTransactions = validationResultTransaction.getErroneousTransactions();
        long violations = erroneousTransactions.size();
        long successfulDataCount = transactionCount - violations;

        if (dbTransactionCount == transactionCount) {
            // generate success report
            LocalDate currentDate = LocalDate.now();
            System.out.println("Date: " + currentDate + " | DBTransactionCount:" + dbTransactionCount + " transactionCount:" + transactionCount);
        }

        LocalDate currentDate = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String formattedDate = currentDate.format(formatter);
        return "Data values fetched successfully from transaction : " + successfulDataCount
                + " | Total data provided :" + transactionCount
                + " | Violations : " + violations
                + " | Date: " + formattedDate;
    }

}
