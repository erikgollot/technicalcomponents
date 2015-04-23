package com.bnpp.ism.dao.application;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.application.Application;

public interface ApplicationDao extends
		CrudRepository<Application, Long> {

}
