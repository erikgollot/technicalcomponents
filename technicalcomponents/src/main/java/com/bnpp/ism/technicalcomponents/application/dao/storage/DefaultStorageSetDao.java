package com.bnpp.ism.technicalcomponents.application.dao.storage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.technicalcomponents.application.model.storage.DefaultStorageSet;

public interface DefaultStorageSetDao extends
		CrudRepository<DefaultStorageSet, Long> {

	@Query("SELECT p FROM DefaultStorageSet p WHERE p.id = 1")
	public DefaultStorageSet getDefault();
}
