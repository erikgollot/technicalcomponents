package com.bnpp.ism.entity.application;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class Application {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Version
	private Long version;
	@Column
	private String name;

	@OneToMany(mappedBy="application",fetch=FetchType.LAZY,cascade=CascadeType.PERSIST)
	List<ApplicationVersion> versions;

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

	public List<ApplicationVersion> getVersions() {
		return versions;
	}

	public void setVersions(List<ApplicationVersion> versions) {
		this.versions = versions;
	}

	public void addVersion(ApplicationVersion v) {
		if (getVersions() == null) {
			this.versions = new ArrayList<ApplicationVersion>();
		}
		getVersions().add(v);
		v.setApplication(this);
	}

	public void removeVersion(ApplicationVersion v) {
		getVersions().remove(v);
	}
}
