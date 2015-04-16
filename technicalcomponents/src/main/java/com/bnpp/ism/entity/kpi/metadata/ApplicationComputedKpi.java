package com.bnpp.ism.entity.kpi.metadata;

import com.bnpp.ism.entity.application.ApplicationVersion;

public interface ApplicationComputedKpi extends ComputedKpi {
	 float compute(ApplicationVersion applicationVersion);
}
