package com.bnpp.ism.entity.kpi.metadata;

public interface ComputedKpi extends Kpi {
	float compute(Object context);

	boolean canCompute(Object context);
}
