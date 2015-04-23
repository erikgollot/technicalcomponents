package com.bnpp.ism.api.exchangedata.application;

import java.util.List;

public class ApplicationView {

	private Long id;

	private Long version;

	private String name;

	List<ApplicationVersionView> versions;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<ApplicationVersionView> getVersions() {
		return versions;
	}

	public void setVersions(List<ApplicationVersionView> versions) {
		this.versions = versions;
	}

}
