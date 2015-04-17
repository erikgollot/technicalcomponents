package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiConfigurationView;

public interface IKpiManager {
	List<AbstractKpiView> loadAllKpiDefinitions();

	KpiConfigurationView createNewConfiguration(String name);
}
