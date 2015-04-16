package com.bnpp.ism.entity.kpi.value;

import java.util.List;

public class KpiSnapshot {
	Long id;
	Long version;
	ManualMeasurement manualMeasurements;
	List<KpiValue> computedKpis;
	
	
	public ManualMeasurement getManualMeasurements() {
		return manualMeasurements;
	}
	public void setManualMeasurements(ManualMeasurement manualMeasurements) {
		this.manualMeasurements = manualMeasurements;
	}
	public List<KpiValue> getComputedKpis() {
		return computedKpis;
	}
	public void setComputedKpis(List<KpiValue> computedKpis) {
		this.computedKpis = computedKpis;
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
}
