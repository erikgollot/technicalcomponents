package com.bnpp.ism.entity.kpi.metadata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

@Entity
public class KpiUsage {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Version
	Long version;
	
	@ManyToOne
	AbstractKpi kpi;
	@Column
	boolean isMandatory;
	@ManyToOne
	KpiConfiguration configuration;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	

	public AbstractKpi getKpi() {
		return kpi;
	}

	public void setKpi(AbstractKpi kpi) {
		this.kpi = kpi;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}

	public KpiConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(KpiConfiguration configuration) {
		this.configuration = configuration;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
