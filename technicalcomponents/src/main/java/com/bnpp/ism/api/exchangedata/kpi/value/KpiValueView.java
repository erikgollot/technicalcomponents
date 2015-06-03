package com.bnpp.ism.api.exchangedata.kpi.value;

import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;

public class KpiValueView {

	Long id;

	Long version;

	float value;	
	
	boolean isSet;

	AbstractKpiView kpi;

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

	public AbstractKpiView getKpi() {
		return kpi;
	}

	public void setKpi(AbstractKpiView kpi) {
		this.kpi = kpi;
	}

	protected boolean isSet() {
		return isSet;
	}

	protected void setSet(boolean isSet) {
		this.isSet = isSet;
	}
}
