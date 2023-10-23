package com.etl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etl.model.Branch;

public interface BranchRepository extends JpaRepository<Branch, Long> {
	
}
