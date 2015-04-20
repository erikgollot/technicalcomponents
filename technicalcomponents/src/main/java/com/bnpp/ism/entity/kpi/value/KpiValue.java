package com.bnpp.ism.entity.kpi.value;

import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;

public class KpiValue {
	Long id;
	Long version;
	float value;
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
