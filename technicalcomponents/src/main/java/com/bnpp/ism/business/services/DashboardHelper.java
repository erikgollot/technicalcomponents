package com.bnpp.ism.business.services;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.bnpp.ism.api.exchangedata.application.ApplicationKpiDashboard;
import com.bnpp.ism.api.exchangedata.application.ApplicationKpiHistory;
import com.bnpp.ism.api.exchangedata.application.DateKpiValue;
import com.bnpp.ism.entity.application.ApplicationVersion;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiEnum;
import com.bnpp.ism.entity.kpi.value.ApplicationVersionKpiSnapshot;
import com.bnpp.ism.entity.kpi.value.KpiValue;
import com.bnpp.ism.entity.kpi.value.ManualUserMeasurement;

public class DashboardHelper {

	public static ApplicationKpiDashboard create(
			List<ApplicationVersion> versions) {
		ApplicationKpiDashboard dashboard = new ApplicationKpiDashboard();
		List<AbstractKpi> uniqueKpis = buildUniqueKpiList(versions);
		for (AbstractKpi kpi : uniqueKpis) {
			createHistory(dashboard, kpi, versions);
		}
		return dashboard;
	}

	private static void createHistory(ApplicationKpiDashboard dashboard,
			AbstractKpi kpi, List<ApplicationVersion> versions) {
		ApplicationKpiHistory history = new ApplicationKpiHistory();
		SimpleDateFormat format = new SimpleDateFormat("yyy-MM-dd");
		history.setKpiName(kpi.getName());
		history.setKpiCategory(kpi.getCategory());
		for (ApplicationVersion version : versions) {
			if (version.getKpiSnapshots() != null) {
				for (ApplicationVersionKpiSnapshot snapshot : version
						.getKpiSnapshots()) {
					// Computed Kpis
					if (snapshot.getComputedKpisValues() != null) {
						for (KpiValue v : snapshot.getComputedKpisValues()) {
							if (v.getKpi() == kpi) {
								DateKpiValue val = new DateKpiValue();
								val.setDate(snapshot.getForDate());
								val.setValue(v.getValue());
								val.setDateStr(format.format(snapshot
										.getForDate()));
								// Retrieve Literal name is KPI is an Enum
								if (kpi instanceof KpiEnum) {
									KpiEnum kpiAsEnum = (KpiEnum) kpi;
									val.setValueAsString(kpiAsEnum
											.getLiteralWithValue(v.getValue())
											.toString());
								}
								history.addValue(val);
							}
						}
					}
					// Measurements
					if (snapshot.getManualMeasurements() != null) {
						for (ManualUserMeasurement measurement : snapshot
								.getManualMeasurements()) {
							float total = 0.0f;
							int num = 0;
							for (KpiValue v : measurement.getValues()) {
								if (v.getKpi() == kpi) {
									num++;
									total += v.getValue();
								}
							}
							total = total / num;
							// Now adjust value...reason is for ENUM Kpi that
							// need to give only literals
							total = kpi.adjustValue(total);
							DateKpiValue val = new DateKpiValue();
							val.setDate(snapshot.getForDate());
							val.setValue(total);
							val.setDateStr(format.format(snapshot.getForDate()));
							if (kpi instanceof KpiEnum) {
								KpiEnum kpiAsEnum = (KpiEnum) kpi;
								val.setValueAsString(kpiAsEnum
										.getLiteralWithValue(total).toString());
							}
							history.addValue(val);
						}
					}
				}
			}
		}
		// Sort history values list by date
		Collections.sort(history.getValues(), new Comparator<DateKpiValue>() {
			@Override
			public int compare(DateKpiValue o1, DateKpiValue o2) {
				return o1.getDate().compareTo(o2.getDate());
			}

		});
		dashboard.addKpiHistory(history);
	}

	// For each snapshot, take unique kpi list
	private static List<AbstractKpi> buildUniqueKpiList(
			List<ApplicationVersion> versions) {
		List<AbstractKpi> uniques = new ArrayList<AbstractKpi>();
		for (ApplicationVersion v : versions) {
			if (v.getKpiSnapshots() != null) {
				for (ApplicationVersionKpiSnapshot snapshot : v
						.getKpiSnapshots()) {
					if (snapshot.getKpis() != null) {
						for (AbstractKpi kpi : snapshot.getKpis()) {
							if (!uniques.contains(kpi)) {
								uniques.add(kpi);
							}
						}
					}
				}
			}
		}
		return uniques;
	}

}
