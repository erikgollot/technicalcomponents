package com.bnpp.ism.dao.storage;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.storage.DefaultStorageSet;

public interface DefaultStorageSetDao extends
		CrudRepository<DefaultStorageSet, Long> {

}
