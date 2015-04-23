package com.bnpp.ism.entity.kpi.value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import com.bnpp.ism.entity.application.ApplicationVersion;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;

@Entity
public class ApplicationVersionKpiSnapshot {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Version
	Long version;
	// Date for which the snapshot has to be considered. It can be different
	// from user measurements or computedkpis. It shoudl not be too different
	// but because users can specify values at different times, it's different.
	// This date cannot be changed is the snapshot is frozen.
	@Column	
	Date forDate;
	// A frozen snapshot can no longer be modified
	@Column
	boolean frozen = false;
	// Manual values entered by differents allowed users
	@OneToMany(mappedBy="snapshot",cascade = CascadeType.ALL)
	List<ManualUserMeasurement> manualMeasurements;
	// Automatic computed kpis
	@OneToMany(cascade = CascadeType.ALL)
	List<KpiValue> computedKpisValues;

	// All Kpis on which this snapshot is based on. Created during snapshot creation
	@ManyToMany
	List<AbstractKpi> kpis;

	@ManyToOne
	ApplicationVersion applicationVersion;

	public void addKpi(AbstractKpi kpi) {
		if (getKpis() == null) {
			this.kpis = new ArrayList<AbstractKpi>();
		}
		getKpis().add(kpi);
	}

	public void addMeasurement(ManualUserMeasurement m) {
		if (getManualMeasurements() == null) {
			this.manualMeasurements = new ArrayList<ManualUserMeasurement>();
		}
		getManualMeasurements().add(m);
		m.setSnapshot(this);
	}

	public void removeMeasurement(ManualUserMeasurement m) {
		getManualMeasurements().remove(m);
	}

	public void addComputedKpiValue(KpiValue val) {
		if (getComputedKpisValues() == null) {
			this.computedKpisValues = new ArrayList<KpiValue>();
		}
		getComputedKpisValues().add(val);
	}

	public void removeComputedKpiValue(KpiValue val) {
		getComputedKpisValues().remove(val);
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

	public List<ManualUserMeasurement> getManualMeasurements() {
		return manualMeasurements;
	}

	public void setManualMeasurements(
			List<ManualUserMeasurement> manualMeasurements) {
		this.manualMeasurements = manualMeasurements;
	}

	public Date getForDate() {
		return forDate;
	}

	public void setForDate(Date forDate) {
		this.forDate = forDate;
	}

	public boolean isFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}

	public ApplicationVersion getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(ApplicationVersion applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	public List<AbstractKpi> getKpis() {
		return kpis;
	}

	public void setKpis(List<AbstractKpi> kpis) {
		this.kpis = kpis;
	}

	public List<KpiValue> getComputedKpisValues() {
		return computedKpisValues;
	}

	public void setComputedKpisValues(List<KpiValue> computedKpisValues) {
		this.computedKpisValues = computedKpisValues;
	}
}
