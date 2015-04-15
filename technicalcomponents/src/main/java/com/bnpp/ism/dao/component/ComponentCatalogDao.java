package com.bnpp.ism.dao.component;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.component.ComponentCatalog;


public interface ComponentCatalogDao extends
		CrudRepository<ComponentCatalog, Long> {
	@Query("SELECT s FROM ComponentCatalog s WHERE s.name = 'Default'")
	public ComponentCatalog getDefault();
}
