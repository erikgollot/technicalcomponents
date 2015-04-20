package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiConfigurationView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiEnumLiteralView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView;

public interface IKpiManager {
	List<AbstractKpiView> loadAllKpiDefinitions();

	KpiConfigurationView createNewConfiguration(String name, String description);

	List<KpiConfigurationView> getAllConfigurations();

	KpiConfigurationView updateConfiguration(KpiConfigurationView config);

	ManualEnumKpiView createEnumKpi(ManualEnumKpiView kpi);

	void updateEnumKpi(ManualEnumKpiView kpi);
	
	void deleteKpi(Long kpiId);

	void deleteLiteral(Long literalId);

	void updateLiteral(KpiEnumLiteralView literal);

	KpiEnumLiteralView addLiteral(Long idKpi, KpiEnumLiteralView literal);

	ManualNumericKpiView createNumericKpi(ManualNumericKpiView kpi);

	void updateNumericKpi(ManualNumericKpiView kpi);
}
