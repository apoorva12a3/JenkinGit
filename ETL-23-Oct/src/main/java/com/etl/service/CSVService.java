package com.etl.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.etl.cleaner.DataCleaner;
import com.etl.helper.CSVHelper;
import com.etl.model.Account;
import com.etl.model.Branch;
import com.etl.model.Customer;
import com.etl.model.Loan;
import com.etl.model.Transaction;
import com.etl.repository.AccountRepository;
import com.etl.repository.BranchRepository;
import com.etl.repository.CustomerRepository;
import com.etl.repository.ErrorRepository;
import com.etl.repository.LoanRepository;
import com.etl.repository.TransactionRepository;
import com.etl.validation.DataValidator;

@Service
public class CSVService {

	@Autowired
	CustomerRepository customerRepo;
	@Autowired
	BranchRepository branchRepo;
	@Autowired
	AccountRepository accountRepo;
	@Autowired
	LoanRepository loanRepo;
	@Autowired
	TransactionRepository transactionRepo;
	@Autowired
	private ErrorRepository errorEntityRepo;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private BranchService branchService;

	@Autowired
	private AccountService accountService;

	@Autowired
	private TransactionService transactionService;

	@Autowired
	private LoanService loanService;

	@Autowired
	DataValidator dataValidator;

	@Autowired
	private DataCleaner dataCleaner;

	List<Customer> customer1 = new ArrayList<>();
	List<Branch> branch1 = new ArrayList<>();
	List<Account> account1 = new ArrayList<>();
	List<Loan> loan1 = new ArrayList<>();
	List<Transaction> transaction1 = new ArrayList<>();

	public void saveInDB(MultipartFile file) {
		try {

			//customer1 = CSVHelper.customer_func(file.getInputStream());

			customer1 = dataCleaner.cleanData(CSVHelper.fetchCustomerDataFromCSV(file.getInputStream()));
			customerService.saveCustomer(customer1);

			//----------------branch-----------
			branch1 = CSVHelper.fetchBranchDataFromCSV(file.getInputStream());
			branchService.saveBranch(branch1);

			//-----------------account-----------------
//            List<Account> account1 = CSVHelper.account_func(file.getInputStream());
			account1 = CSVHelper.fetchAccountDataFromCSV(file.getInputStream());
			accountService.saveAccount(account1);

			//---------------transactions----
			transaction1 = CSVHelper.fetchTransactionDataFromCSV(file.getInputStream());
			transactionService.saveTransaction(transaction1);

			//-------------------LOAN-----
			loan1= CSVHelper.fetchLoanDataFromCSV(file.getInputStream());
			loanService.saveLoan(loan1);

		} catch (IOException e) {
			throw new RuntimeException("failed to store csv data: " + e.getMessage());
		}
	}
	public void justDisplay() {
		System.out.println("Just displaying customers");
		for(Customer customer : customer1) {
			System.out.println(customer.toString());
		}
	}


	// ----------------------Combined count----------
	public String getCombinedReport() {
		String customerReport = customerService.getCustomerCountForReport(customer1);
		String accountReport = accountService.getAccountCountForReport(account1);
		String branchReport = branchService.getBranchCountForReport(branch1);
		String loanReport = loanService.getLoanCountForReport(loan1);
		String transactionReport = transactionService.getTransactionCountForReport(transaction1);

		return customerReport + "\n" + accountReport + "\n" + branchReport + "\n" + loanReport + "\n" + transactionReport;
	}
}
