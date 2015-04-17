package com.bnpp.ism.api.exchangedata.kpi.metadata;

public class KpiUsageView {

	Long id;

	Long version;

	AbstractKpiView kpi;

	boolean isMandatory;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
