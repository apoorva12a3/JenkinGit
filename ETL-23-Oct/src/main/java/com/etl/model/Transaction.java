package com.etl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Date;

import com.etl.validation.ValidDateTimeFormat;

@Entity
@Table(name = "transactions")
public class Transaction {

    @Id
    @Column(name = "TransactionID", nullable = false)
    private Long transactionID;

    @Column(name = "AccountID", nullable = false)
    private Long accountID;

    @NotEmpty(message = "Transaction Type cannot be empty")
    @Pattern(regexp = "^(Transfer|Withdrawal|Deposit)$", message = "Invalid transaction type")
    @Column(name = "TransactionType", nullable = false)
    private String transactionType;

    @Min(value = 0, message = "Transaction Amount must be greater than or equal to 0")
    @Max(value = 100000, message = "Transaction Amount cannot exceed 10,00,00")
    @Column(name = "Amount", nullable = false)
    private Double amount;

    @NotNull(message = "Transaction Date cannot be null")
    @ValidDateTimeFormat(format = "dd-MM-yyyy HH:mm:ss")
    @Column(name = "TransactionDate", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    public Transaction() {
    }

    public Transaction(Long transactionID, Long accountID, String transactionType, Double amount, Date transactionDate) {
        this.transactionID = transactionID;
        this.accountID = accountID;
        this.transactionType = transactionType;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Long getTransactionID() {
        return transactionID;
    }

    public void setTransactionID(Long transactionID) {
        this.transactionID = transactionID;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

	@Override
	public String toString() {
		return "Transaction [transactionID=" + transactionID + ", accountID=" + accountID + ", transactionType="
				+ transactionType + ", amount=" + amount + ", transactionDate=" + transactionDate + "]";
	}
    
}
