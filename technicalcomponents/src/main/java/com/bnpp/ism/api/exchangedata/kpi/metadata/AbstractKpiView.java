package com.bnpp.ism.api.exchangedata.kpi.metadata;

import com.bnpp.ism.api.exchangedata.kpi.application.ApplicationObsolescenceKpiView;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
@JsonSubTypes({
		@com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = ManualEnumKpiView.class,name="a"),
		@com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = ManualNumericKpiView.class,name="b"),
		@com.fasterxml.jackson.annotation.JsonSubTypes.Type(value = ApplicationObsolescenceKpiView.class,name="c") })
public abstract class AbstractKpiView {
	
	Long id;

	Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	String name;

	String description;

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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
