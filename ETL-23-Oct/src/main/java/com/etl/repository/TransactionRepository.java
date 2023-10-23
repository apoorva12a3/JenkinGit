package com.etl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etl.model.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
	
}
