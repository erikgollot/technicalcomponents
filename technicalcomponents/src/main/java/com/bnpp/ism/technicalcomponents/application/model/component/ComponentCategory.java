package com.bnpp.ism.technicalcomponents.application.model.component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ComponentCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private String description;

	@OneToMany(mappedBy="parent")
	private List<ComponentCategory> categories;
	
	@ManyToOne
	ComponentCategory parent;
	
	@OneToMany
	private List<TechnicalComponent> technicalComponents;

	
	
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

	public void setCategories(List<ComponentCategory> subCategories) {
		this.categories = subCategories;
	}

	public List<TechnicalComponent> getTechnicalComponents() {
		return technicalComponents;
	}

	public void setTechnicalComponents(
			List<TechnicalComponent> technicalComponents) {
		this.technicalComponents = technicalComponents;
	}

	public void addTechnicalComponent(TechnicalComponent component) {
		if (this.getTechnicalComponents() == null) {
			this.technicalComponents = new ArrayList<TechnicalComponent>();
		}
		getTechnicalComponents().add(component);
	}

	public void removeTechnicalComponent(TechnicalComponent component) {
		getTechnicalComponents().remove(component);
	}

	public void addComponentCategory(ComponentCategory category) {
		if (this.getCategories() == null) {
			this.categories = new ArrayList<ComponentCategory>();
		}
		getCategories().add(category);
	}

	public void removeComponentCategory(ComponentCategory category) {
		getCategories().remove(category);
	}

	public ComponentCategory getParent() {
		return parent;
	}

	public void setParent(ComponentCategory parent) {
		this.parent = parent;
	}
}
