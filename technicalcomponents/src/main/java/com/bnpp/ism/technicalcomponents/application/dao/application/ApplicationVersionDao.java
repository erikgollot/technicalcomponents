package com.bnpp.ism.technicalcomponents.application.dao.application;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.technicalcomponents.application.model.application.ApplicationVersion;

public interface ApplicationVersionDao extends
		CrudRepository<ApplicationVersion, Long> {

}
