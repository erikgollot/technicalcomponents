package com.bnpp.ism.api.exchangedata.kpi.metadata;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;


public class KpiUsageView {
	
	Long id;
	
	Long version;
	
	int rank;
	
	AbstractKpiView kpi;
	
	boolean isMandatory;
		

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getRank() {
		return rank;
	}

	public void setRank(int rank) {
		this.rank = rank;
	}

	public AbstractKpiView getKpi() {
		return kpi;
	}

	public void setKpi(AbstractKpiView kpi) {
		this.kpi = kpi;
	}

	public boolean isMandatory() {
		return isMandatory;
	}

	public void setMandatory(boolean isMandatory) {
		this.isMandatory = isMandatory;
	}
	

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
