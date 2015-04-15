package com.bnpp.ism.api.exchangedata.component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ComponentCatalogView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 256246412399411062L;

	private Long id;
	private Long version;
	List<ObsolescenceStrategyView> obscolescenceStrategies;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

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

	public void addObscolescenceStrategy(ObsolescenceStrategyView s) {
		if (getObscolescenceStrategies() == null) {
			this.obscolescenceStrategies = new ArrayList<ObsolescenceStrategyView>();
		}
		getObscolescenceStrategies().add(s);
	}

	public void removeObscolescenceStrategy(ObsolescenceStrategyView s) {
		if (getObscolescenceStrategies() != null) {
			getObscolescenceStrategies().remove(s);
		}

	}

	public List<ObsolescenceStrategyView> getObscolescenceStrategies() {
		return obscolescenceStrategies;
	}

	public void setObscolescenceStrategies(
			List<ObsolescenceStrategyView> obscolescenceStrategies) {
		this.obscolescenceStrategies = obscolescenceStrategies;
	}
}
