package com.bnpp.ism.api;

import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiListView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiEnumLiteralView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView;

public interface IKpiManager {
	AbstractKpiListView loadAllKpiDefinitions();

	ManualEnumKpiView createEnumKpi(ManualEnumKpiView kpi);

	void updateEnumKpi(ManualEnumKpiView kpi);

	void deleteKpi(Long kpiId);

	void deleteLiteral(Long literalId);

	void updateLiteral(KpiEnumLiteralView literal);

	KpiEnumLiteralView addLiteral(Long idKpi, KpiEnumLiteralView literal);

	ManualNumericKpiView createNumericKpi(ManualNumericKpiView kpi);

	void updateNumericKpi(ManualNumericKpiView kpi);

}
