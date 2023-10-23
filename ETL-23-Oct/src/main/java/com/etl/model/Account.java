package com.etl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "accounts")
public class Account {

    @Id
    @Column(name = "AccountID", nullable = false)
    private Long accountID;

    @Column(name = "CustomerID", nullable = false)
    private Long customerID;

    @Column(name = "BankID", nullable = false)
    private Long bankID;

    @NotBlank(message="accountype cannot be blank")
    @Column(name = "Type", nullable = false)
    private String type;

    @NotNull(message="balance cannot be null")
    @Min(value=2500,message="The minimum balance should be greater than 2500")
    @Column(name = "Balance", nullable = false)
    private Double balance;
    public Account() {
    }
    public Account(Long accountID, Long customerID, Long bankID, String type, Double balance) {
        this.accountID = accountID;
        this.customerID = customerID;
        this.bankID = bankID;
        this.type = type;
        this.balance = balance;
    }

    public Long getAccountID() {
        return accountID;
    }

    public void setAccountID(Long accountID) {
        this.accountID = accountID;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public Long getBankID() {
        return bankID;
    }

    @Override
	public String toString() {
		return "Account [accountID=" + accountID + ", customerID=" + customerID + ", bankID=" + bankID + ", type="
				+ type + ", balance=" + balance + "]";
	}

	public void setBankID(Long bankID) {
        this.bankID = bankID;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }
}