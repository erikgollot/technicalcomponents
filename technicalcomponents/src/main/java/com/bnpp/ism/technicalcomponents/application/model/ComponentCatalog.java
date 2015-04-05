package com.bnpp.ism.technicalcomponents.application.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

public class ComponentCatalog {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private String description;
	
	@OneToMany
	private List<ComponentCategory> categories;
	
	public void addComponentCategory(ComponentCategory category) {
		if (this.getCategories() == null) {
			this.categories = new ArrayList<ComponentCategory>();
		}
		getCategories().add(category);
	}

	public void removeComponentCategory(ComponentCategory category) {
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

	public List<ComponentCategory> getCategories() {
		return categories;
	}

	public void setCategories(List<ComponentCategory> categories) {
		this.categories = categories;
	}
}
