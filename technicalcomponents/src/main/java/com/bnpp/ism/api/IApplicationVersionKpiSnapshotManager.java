package com.bnpp.ism.api;

import java.util.Date;
import java.util.List;

import com.bnpp.ism.api.exchangedata.application.ApplicationKpiDashboard;
import com.bnpp.ism.api.exchangedata.kpi.value.ApplicationVersionKpiSnapshotView;
import com.bnpp.ism.api.exchangedata.kpi.value.KpiValueView;
import com.bnpp.ism.api.exchangedata.kpi.value.ManualUserMeasurementView;

public interface IApplicationVersionKpiSnapshotManager {
	ApplicationVersionKpiSnapshotView createSnapshot(Long idApplicationVersion);

	List<ApplicationVersionKpiSnapshotView> getSnapshots(
			Long idApplicationVersion);

	ApplicationVersionKpiSnapshotView updateSnapshot(Long snapshotId,
			boolean frozen, Date forDate);

	List<KpiValueView> computeAutmaticApplicationKpis(Long snapshotId);

	ManualUserMeasurementView createMeasurement(Long snapshotId, Long userId);

	ManualUserMeasurementView updateMeasurement(
			ManualUserMeasurementView measurement);

	void deleteSnapshot(Long snapshotId);

	void deleteMeasurement(Long measurementId);

	ApplicationKpiDashboard getApplicationDashboard(Long applicationId);
}
