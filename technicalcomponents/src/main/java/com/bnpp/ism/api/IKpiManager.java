package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiEnumLiteralView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView;
import com.bnpp.ism.api.exchangedata.kpi.value.ApplicationVersionKpiSnapshotView;
import com.bnpp.ism.api.exchangedata.kpi.value.ManualUserMeasurementView;

public interface IKpiManager {
	List<AbstractKpiView> loadAllKpiDefinitions();

	ManualEnumKpiView createEnumKpi(ManualEnumKpiView kpi);

	void updateEnumKpi(ManualEnumKpiView kpi);

	void deleteKpi(Long kpiId);

	void deleteLiteral(Long literalId);

	void updateLiteral(KpiEnumLiteralView literal);

	KpiEnumLiteralView addLiteral(Long idKpi, KpiEnumLiteralView literal);

	ManualNumericKpiView createNumericKpi(ManualNumericKpiView kpi);

	void updateNumericKpi(ManualNumericKpiView kpi);
	
}
