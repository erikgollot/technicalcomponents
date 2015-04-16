package com.bnpp.ism.entity.kpi.metadata;

import com.bnpp.ism.entity.application.ApplicationVersion;

public interface ComputedKpi extends Kpi {
	float compute(Object context);
}
