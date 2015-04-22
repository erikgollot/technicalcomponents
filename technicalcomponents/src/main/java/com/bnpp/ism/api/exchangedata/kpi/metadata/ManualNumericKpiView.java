package com.bnpp.ism.api.exchangedata.kpi.metadata;

import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;

public class ManualNumericKpiView extends AbstractKpiView {
	String clazz = "com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView";
	Float minValue;

	Float maxValue;

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
	

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	

}
