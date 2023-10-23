package com.etl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "customers")
public class Customer {

    @Id
    @Column(name = "CustomerID", nullable = false)
    private Long customerID;

    @NotEmpty(message = "First name cannot be empty")
    @Size(max = 255, message = "First name must not exceed 255 characters")
    @Column(name = "FirstName", nullable = false)
    private String firstName;

    @NotEmpty(message = "Last name cannot be empty")
    @Size(max = 255, message = "Last name must not exceed 255 characters")
    @Column(name = "LastName", nullable = false)
    private String lastName;

    @NotEmpty(message = "Address cannot be empty")
    @Size(max = 1000, message = "Address must not exceed 1000 characters")
    @Column(name = "Address", nullable = false)
    private String address;

    @Pattern(regexp = "\\+91 [6-9]\\d{9}", message = "Contact number Must start with +91 and have 10 digits, starting with 7, 8, or 9")
    @Column(name = "ContactNo", nullable = false)
    private String contactNo;
    @NotEmpty(message = "Email list cannot be empty")
    @Email(message = "Invalid email format")
    @Column(name = "EmailID", nullable = false)
    private String emailID;

    public Customer() {
    }

    public Customer(long customerID, String firstName, String lastName, String address, String contactNo, String emailID) {
        this.customerID = customerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.contactNo = contactNo;
        this.emailID = emailID;
    }

    public Long getCustomerID() {
        return customerID;
    }

    public void setCustomerID(Long customerID) {
        this.customerID = customerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactNo() {
        return contactNo;
    }

    public void setContactNo(String contactNo) {
        this.contactNo = contactNo;
    }

    public String getEmailID() {
        return emailID;
    }

    public void setEmailID(String emailID) {
        this.emailID = emailID;
    }

	@Override
	public String toString() {
		return "Customer [customerID=" + customerID + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", address=" + address + ", contactNo=" + contactNo + ", emailID=" + emailID + "]";
	}
    
}
