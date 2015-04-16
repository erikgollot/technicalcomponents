package com.bnpp.ism.entity.kpi.metadata;

public interface Kpi {

	Long getId();

	String getName();

	String getDescription();

	KpiKindEnum getKind();

	float adjustValue(float value);
}
