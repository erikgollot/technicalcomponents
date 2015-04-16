package com.bnpp.ism.entity.kpi.metadata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class KpiEnumLiteral {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Version
	Long version;
	@Column
	int rank;
	@Column(columnDefinition = "VARCHAR(50)")
	String name;
	@Column
	float value;

	@ManyToOne
	AbstractKpi enumKpi;
	
	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

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

	public AbstractKpi getEnumKpi() {
		return enumKpi;
	}

	public void setEnumKpi(AbstractKpi enumKpi) {
		this.enumKpi = enumKpi;
	}

}
