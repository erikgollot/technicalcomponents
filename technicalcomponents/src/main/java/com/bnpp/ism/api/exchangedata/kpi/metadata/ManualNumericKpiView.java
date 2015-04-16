package com.bnpp.ism.api.exchangedata.kpi.metadata;

import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;

public class ManualNumericKpiView extends AbstractKpiView {

	Float minValue;

	Float maxValue;

	boolean isInt = false;
	KpiKindEnum kind;

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

	public KpiKindEnum getKind() {
		return KpiKindEnum.MANUAL;
	}

	public void setKind(KpiKindEnum kind) {
		this.kind = kind;
	}
}
