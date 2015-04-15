package com.bnpp.ism.entity.component;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class ComponentCatalog {
	public static final String DEFAULT = "Default";

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

	@Column
	private String name;

	@Column
	private String description;

	@OneToMany(cascade = CascadeType.ALL)
	private List<ComponentCategory> categories;

	@ManyToMany
	List<ObsolescenceStrategy> obscolescenceStrategies;

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

	public List<ObsolescenceStrategy> getObscolescenceStrategies() {
		return obscolescenceStrategies;
	}

	public void setObscolescenceStrategies(
			List<ObsolescenceStrategy> obscolescenceStrategies) {
		this.obscolescenceStrategies = obscolescenceStrategies;
	}

	public void addObscolescenceStrategy(ObsolescenceStrategy s) {
		if (getObscolescenceStrategies() == null) {
			this.obscolescenceStrategies = new ArrayList<ObsolescenceStrategy>();
		}
		getObscolescenceStrategies().add(s);
	}

	public void removeObscolescenceStrategy(ObsolescenceStrategy s) {
		if (getObscolescenceStrategies() != null) {
			getObscolescenceStrategies().remove(s);
		}

	}
}
