package com.bnpp.ism.technicalcomponents.application.model.view.component;

import java.io.Serializable;
import java.util.Date;

public class ComponentVersionInfoView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4735660414482931914L;

	private String name;

	private String version;

	private String description;

	private String availableDate;

	private String deprecatedDate;

	private String unavailableDate;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAvailableDate() {
		return availableDate;
	}

	public void setAvailableDate(String availableDate) {
		this.availableDate = availableDate;
	}

	public String getDeprecatedDate() {
		return deprecatedDate;
	}

	public void setDeprecatedDate(String deprecatedDate) {
		this.deprecatedDate = deprecatedDate;
	}

	public String getUnavailableDate() {
		return unavailableDate;
	}

	public void setUnavailableDate(String unavailableDate) {
		this.unavailableDate = unavailableDate;
	}

	
}
