package com.bnpp.ism.dao.component;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.component.ComponentGallery;


public interface ComponentGalleryDao extends
		CrudRepository<ComponentGallery, Long> {
	
}
