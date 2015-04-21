package com.bnpp.ism.entity.kpi.metadata;

import com.bnpp.ism.entity.application.ApplicationVersion;

public abstract class ApplicationComputedKpi extends AbstractKpi implements
		ComputedKpi {
	public abstract float compute(ApplicationVersion applicationVersion);

	public abstract boolean canCompute(ApplicationVersion applicationVersion);
}
