package com.bnpp.ism.technicalcomponents.application.model.view.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class ComponentCatalogView implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 256246412399411062L;


	private Long id;

	
	private String name;

	
	private String description;
	
	
	private List<ComponentCategoryView> categories;
	
	public void addComponentCategory(ComponentCategoryView category) {
		if (this.getCategories() == null) {
			this.categories = new ArrayList<ComponentCategoryView>();
		}
		getCategories().add(category);
	}

	public void removeComponentCategory(ComponentCategoryView category) {
		getCategories().remove(category);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ComponentCategoryView> getCategories() {
		return categories;
	}

	public void setCategories(List<ComponentCategoryView> categories) {
		this.categories = categories;
	}
}
