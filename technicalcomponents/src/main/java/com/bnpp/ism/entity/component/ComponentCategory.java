package com.bnpp.ism.entity.component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class ComponentCategory {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Version
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Column(columnDefinition="VARCHAR(100)")
	private String name;

	@Column(columnDefinition="VARCHAR(255)")
	private String description;

	@OneToMany(mappedBy = "parent", cascade = CascadeType.ALL)
	private List<ComponentCategory> categories;

	@ManyToOne
	ComponentCategory parent;

	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
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
		component.setCategory(this);
	}

	public void removeTechnicalComponent(TechnicalComponent component) {
		getTechnicalComponents().remove(component);
		component.setCategory(null);
	}

	public void addComponentCategory(ComponentCategory category) {
		if (this.getCategories() == null) {
			this.categories = new ArrayList<ComponentCategory>();
		}
		getCategories().add(category);
		category.setParent(this);
	}

	public void removeComponentCategory(ComponentCategory category) {
		getCategories().remove(category);
		category.setParent(null);
	}

	public ComponentCategory getParent() {
		return parent;
	}

	public void setParent(ComponentCategory parent) {
		this.parent = parent;
	}

	public String getFullPathname() {
		if (getParent() != null) {
			return getParent().getFullPathname() + "/" + getName();
		} else {
			return getName();
		}
	}
}
