package com.bnpp.ism.api.exchangedata.kpi.metadata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Version;

@Entity
public class UnitView {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Version
	Long version;

	@Column(columnDefinition = "VARCHAR(50)")
	String value;

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

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
