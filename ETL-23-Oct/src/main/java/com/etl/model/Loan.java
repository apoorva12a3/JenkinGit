package com.etl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Entity
@Table(name = "loans")
public class Loan {

    @Id
    @Column(name = "LoanID", nullable = false)
    private Long loanID;

    @Column(name = "CustomerID", nullable = false)
    private Long customerID;

    @Column(name = "BankID", nullable = false)
    private Long bankID;

    @NotNull(message = "LoanAmount cannot be null")
    @Min(value = 5001, message = "Loan amount must be greater than 5000")
    @Column(name = "LoanAmount", nullable = false)
    private Double loanAmount;

    @NotBlank(message = "LoanType cannot be blank")
    @Column(name = "LoanType", nullable = false)
    private String loanType;

    @NotNull(message = "InterestRate cannot be null")
    @DecimalMin(value = "0.1", message = "Interest rate must be greater than or equal to 0.1%")
    @DecimalMax(value = "10", message = "Interest rate must be less than or equal to 10%")
    @Column(name = "InterestRate", nullable = false)
    private Double interestRate;

    @NotBlank(message = "Term cannot be Blank")
    @Pattern(regexp = "^[A-Za-z0-9\\s]+$", message = "Term should consist of alphanumeric characters, including white spaces")
    @Column(name = "Term", nullable = false)
    private String term;

    @NotBlank(message = "Status cannot be blank")
    @Column(name = "Status", nullable = false)
    private String status;

    public Loan() {
    }

    public Loan(Long loanID, Long customerID, Long bankID, Double loanAmount, String loanType, Double interestRate, String term, String status) {
        this.loanID = loanID;
        this.customerID = customerID;
        this.bankID = bankID;
        this.loanAmount = loanAmount;
        this.loanType = loanType;
        this.interestRate = interestRate;
        this.term = term;
        this.status = status;
    }

    public Long getLoanID() {
        return loanID;
    }

    public void setLoanID(Long loanID) {
        this.loanID = loanID;
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

    public void setBankID(Long bankID) {
        this.bankID = bankID;
    }

    public Double getLoanAmount() {
        return loanAmount;
    }

    public void setLoanAmount(Double loanAmount) {
        this.loanAmount = loanAmount;
    }

    public String getLoanType() {
        return loanType;
    }

    public void setLoanType(String loanType) {
        this.loanType = loanType;
    }

    public Double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(Double interestRate) {
        this.interestRate = interestRate;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

	@Override
	public String toString() {
		return "Loan [loanID=" + loanID + ", customerID=" + customerID + ", bankID=" + bankID + ", loanAmount="
				+ loanAmount + ", loanType=" + loanType + ", interestRate=" + interestRate + ", term=" + term
				+ ", status=" + status + "]";
	}
    
}