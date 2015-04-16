package com.bnpp.ism.entity.kpi.value;

import java.util.ArrayList;
import java.util.List;

public class ManualMeasurement {
	Long id;
	Long version;
	List<ManualUserMeasurement> measurements;

	public List<ManualUserMeasurement> getMeasurements() {
		return measurements;
	}

	public void setMeasurements(List<ManualUserMeasurement> measurements) {
		this.measurements = measurements;
	}

	public void addMeasurement(ManualUserMeasurement m) {
		if (getMeasurements() == null) {
			this.measurements = new ArrayList<ManualUserMeasurement>();
		}
		getMeasurements().add(m);
	}

	public void removeMeasurement(ManualUserMeasurement m) {
		getMeasurements().remove(m);
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
