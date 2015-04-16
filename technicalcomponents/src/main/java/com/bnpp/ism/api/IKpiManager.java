package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;

public interface IKpiManager {
	List<AbstractKpiView> loadAllKpiDefinitions();
}
