package com.bnpp.ism.technicalcomponents.application.service.component;

import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCatalog;
import com.bnpp.ism.technicalcomponents.application.model.component.TechnicalComponent;

public interface ComponentCatalogService {
	ComponentCatalog createCatalog(String name, String description);

	Iterable<TechnicalComponent> getAllComponents();

	Iterable<ComponentCatalog> catalogs();
	
	boolean moveCategory(Long categoryId,Long newParentId);
}