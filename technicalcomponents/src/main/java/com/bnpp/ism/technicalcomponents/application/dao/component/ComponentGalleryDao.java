package com.bnpp.ism.technicalcomponents.application.dao.component;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.technicalcomponents.application.model.component.ComponentGallery;


public interface ComponentGalleryDao extends
		CrudRepository<ComponentGallery, Long> {
	
}
