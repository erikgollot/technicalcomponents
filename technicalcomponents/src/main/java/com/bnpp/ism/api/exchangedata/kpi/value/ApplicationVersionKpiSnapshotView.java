package com.bnpp.ism.api.exchangedata.kpi.value;

import java.util.Date;
import java.util.List;

import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;

public class ApplicationVersionKpiSnapshotView {

	Long id;

	Long version;
	// Date for which the snapshot has to be considered. It can be different
	// from user measurements or computedkpis. It shoudl not be too different
	// but because users can specify values at different times, it's different.
	// This date cannot be changed is the snapshot is frozen.

	String forDate;
	// A frozen snapshot can no longer be modified

	boolean frozen = false;
	// Manual values entered by differents allowed users

	List<ManualUserMeasurementView> manualMeasurements;
	// Automatic computed kpis
	List<KpiValueView> computedKpis;

	List<AbstractKpiView> kpis;

	public List<KpiValueView> getComputedKpis() {
		return computedKpis;
	}

	public void setComputedKpis(List<KpiValueView> computedKpis) {
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

	public List<ManualUserMeasurementView> getManualMeasurements() {
		return manualMeasurements;
	}

	public void setManualMeasurements(
			List<ManualUserMeasurementView> manualMeasurements) {
		this.manualMeasurements = manualMeasurements;
	}
	

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public List<AbstractKpiView> getKpis() {
		return kpis;
	}

	public void setKpis(List<AbstractKpiView> kpis) {
		this.kpis = kpis;
	}

	public String getForDate() {
		return forDate;
	}

	public void setForDate(String forDate) {
		this.forDate = forDate;
	}
}
