package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.component.ComponentCatalogView;
import com.bnpp.ism.api.exchangedata.component.ComponentCategoryView;
import com.bnpp.ism.api.exchangedata.component.TechnicalComponentView;
import com.bnpp.ism.entity.component.ComponentCatalog;
import com.bnpp.ism.entity.component.TechnicalComponent;

public interface IComponentCatalogManager {
	ComponentCatalog createCatalog(String name, String description);

	Iterable<TechnicalComponent> getAllComponents();

	List<TechnicalComponentView> getComponentsOfCategory(Long categoryId);

	Iterable<ComponentCatalogView> catalogs();

	boolean moveCategory(Long categoryId, Long newParentId);

	TechnicalComponent createComponent(TechnicalComponentView toCreate,
			Long parentCategoryId);

	TechnicalComponent updateComponent(TechnicalComponentView toUpdate);

	TechnicalComponent setImageComponent(Long componentId, Long imageId);

	List<TechnicalComponentView> searchTechnicalComponentsWithStatus(
			String status);

	void deleteComponent(Long componentId);

	ComponentCategoryView addCategory(String name, Long parentCategoryId);

	void deleteCategory(Long categoryId);
	
	void changeCategoryName(Long categoryId,String newName);
	
	List<TechnicalComponentView> searchComponentsFromFullPathName(String regexp);
	
	void evictCachedComponentsFromFullPathName();
	
	public static final String SEARCH_COMPONENTS_FROM_FULL_PATHNAME_CACHE = "searchComponentsFromFullPathName";
}