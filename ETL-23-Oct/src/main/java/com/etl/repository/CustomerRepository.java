package com.etl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etl.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
}
