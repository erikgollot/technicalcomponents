package com.bnpp.ism.entity.kpi.metadata;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("ManualNumericKpi")
public class ManualNumericKpi extends AbstractKpi {
	@Column
	Float minValue;
	@Column
	Float maxValue;
	@Column
	boolean isInt = false;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Float getMinValue() {
		return minValue;
	}

	public void setMinValue(Float minValue) {
		this.minValue = minValue;
	}

	public Float getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(Float maxValue) {
		this.maxValue = maxValue;
	}

	public boolean isInt() {
		return isInt;
	}

	public void setInt(boolean isInt) {
		this.isInt = isInt;
	}

	public float adjustValue(float value) {
		// Nothing todo for numeric value
		return value;
	}
	
}
