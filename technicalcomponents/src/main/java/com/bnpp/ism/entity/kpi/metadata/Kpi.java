package com.bnpp.ism.entity.kpi.metadata;

public interface Kpi {

	Long getId();

	String getName();

	String getDescription();

	KpiKindEnum getKind();
	
	boolean acceptValue(float value);

	float adjustValue(float value);
}
