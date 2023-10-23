package com.etl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etl.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
	
}
