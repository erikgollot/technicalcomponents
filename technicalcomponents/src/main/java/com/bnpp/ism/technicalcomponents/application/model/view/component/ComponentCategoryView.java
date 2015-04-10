package com.bnpp.ism.technicalcomponents.application.model.view.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComponentCategoryView  implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5079820437993514066L;

	private Long id;
private Long version;
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	private String name;

	private String description;

	private List<ComponentCategoryView> categories;

	private List<TechnicalComponentView> technicalComponents;

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

	public void setCategories(List<ComponentCategoryView> subCategories) {
		this.categories = subCategories;
	}

	public List<TechnicalComponentView> getTechnicalComponents() {
		return technicalComponents;
	}

	public void setTechnicalComponents(
			List<TechnicalComponentView> technicalComponents) {
		this.technicalComponents = technicalComponents;
	}

	public void addTechnicalComponent(TechnicalComponentView component) {
		if (this.getTechnicalComponents() == null) {
			this.technicalComponents = new ArrayList<TechnicalComponentView>();
		}
		getTechnicalComponents().add(component);
	}

	public void removeTechnicalComponent(TechnicalComponentView component) {
		getTechnicalComponents().remove(component);
	}

	public void addComponentCategory(ComponentCategoryView category) {
		if (this.getCategories() == null) {
			this.categories = new ArrayList<ComponentCategoryView>();
		}
		getCategories().add(category);
	}

	public void removeComponentCategory(ComponentCategoryView category) {
		getCategories().remove(category);
	}
}
