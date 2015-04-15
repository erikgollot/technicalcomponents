package com.bnpp.ism.dao.application;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.application.ApplicationVersion;

public interface ApplicationVersionDao extends
		CrudRepository<ApplicationVersion, Long> {

}
