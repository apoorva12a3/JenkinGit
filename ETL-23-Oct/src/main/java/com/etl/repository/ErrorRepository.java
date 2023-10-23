package com.etl.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.etl.model.ErrorEntity;

public interface ErrorRepository extends JpaRepository<ErrorEntity, Long> {

}