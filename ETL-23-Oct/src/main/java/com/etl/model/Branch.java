package com.etl.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "branches")
public class Branch {

    @Id
    @Column(name = "BranchID", nullable = false)
    private Long branchID;

    @NotEmpty(message = "Branch Location cannot be empty")
    @Size(min = 5, max = 255, message = "Branch location should be between 5 and 255 characters")
    @Column(name = "BranchLocation", nullable = false)
    private String branchLocation;

    @NotEmpty(message = "Adress cannot be empty")
    @Pattern(regexp = "^AX\\d{5}$", message = "Branch code must start with 'AX' followed by 5 digits")
    @Column(name = "BranchCode", nullable = false, unique = true)
    private String branchCode;

    @NotEmpty(message = "Branch Address cannot be empty")
    @Size(max = 255, message = "Address must not exceed 255 characters")
    @Column(name = "BranchAddress", nullable = false)
    private String branchAddress;

    @Pattern(regexp = "\\+91 \\d{10}", message = "Invalid contact number format. Must start with +91 and have 10 digits.")
    @Column(name = "BranchContactNo", nullable = false)
    private String branchContactNo;

    @Override
	public String toString() {
		return "Branch [branchID=" + branchID + ", branchLocation=" + branchLocation + ", branchCode=" + branchCode
				+ ", branchAddress=" + branchAddress + ", branchContactNo=" + branchContactNo + "]";
	}

	public Branch() {
    }

    public Branch(long branchID, String branchLocation, String branchCode, String branchAddress, String branchContactNo) {
        this.branchID = branchID;
        this.branchLocation = branchLocation;
        this.branchCode = branchCode;
        this.branchAddress = branchAddress;
        this.branchContactNo = branchContactNo;
    }


    public Long getBranchID() {
        return branchID;
    }

    public void setBranchID(Long branchID) {
        this.branchID = branchID;
    }

    public String getBranchLocation() {
        return branchLocation;
    }

    public void setBranchLocation(String branchLocation) {
        this.branchLocation = branchLocation;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }

    public String getBranchContactNo() {
        return branchContactNo;
    }

    public void setBranchContactNo(String branchContactNo) {
        this.branchContactNo = branchContactNo;
    }
}
