package com.bnpp.ism.technicalcomponents.application.service.component;

import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCatalog;
import com.bnpp.ism.technicalcomponents.application.model.component.TechnicalComponent;
import com.bnpp.ism.technicalcomponents.application.model.view.component.ComponentCatalogView;
import com.bnpp.ism.technicalcomponents.application.model.view.component.TechnicalComponentView;

public interface ComponentCatalogService {
	ComponentCatalog createCatalog(String name, String description);

	Iterable<TechnicalComponent> getAllComponents();

	Iterable<ComponentCatalogView> catalogs();

	boolean moveCategory(Long categoryId, Long newParentId);

	TechnicalComponent createComponent(TechnicalComponentView toCreate,
			Long parentCategoryId);

	TechnicalComponent updateComponent(TechnicalComponentView toUpdate);

	TechnicalComponent setImageComponent(Long componentId, Long imageId);
}