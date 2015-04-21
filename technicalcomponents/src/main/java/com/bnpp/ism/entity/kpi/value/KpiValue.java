package com.bnpp.ism.entity.kpi.value;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;

@Entity
public class KpiValue {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Version
	Long version;
	@Column
	float value;
	@ManyToOne
	AbstractKpi kpi;

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

	public AbstractKpi getKpi() {
		return kpi;
	}

	public void setKpi(AbstractKpi kpi) {
		this.kpi = kpi;
	}
}
