package com.bnpp.ism.entity.kpi.metadata;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Lob;
import javax.persistence.Version;

import com.bnpp.ism.diffmerge.stereotype.DiffMergeIgnore;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "BASE_KPI_TYPE", discriminatorType = DiscriminatorType.STRING, columnDefinition="VARCHAR(50)")
public abstract class AbstractKpi implements Kpi, Comparable<AbstractKpi> {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@DiffMergeIgnore
	Long id;
	@Version
	@DiffMergeIgnore
	Long version;

	@Column(columnDefinition = "VARCHAR(32)")
	KpiKindEnum kind;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Column(columnDefinition = "VARCHAR(100)")
	String name;
	
	@Column(columnDefinition = "VARCHAR(30)")
	String shortName;
	
	@Lob
	@Column(length=5000)
	@Basic(fetch = FetchType.LAZY)
	String description;

	// From Unit table
	@Column(columnDefinition = "VARCHAR(50)")
	String unit;

	@Column(columnDefinition = "VARCHAR(50)")
	String category;

	@Column
	boolean isActive = true;

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

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public boolean isActive() {
		return isActive;
	}

	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

	public KpiKindEnum getKind() {
		return kind;
	}

	public void setKind(KpiKindEnum kind) {
		this.kind = kind;
	}

	@Override
	public int compareTo(AbstractKpi o) {
		return getId().compareTo(o.getId());
	}

	public String getShortName() {
		return shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

}
