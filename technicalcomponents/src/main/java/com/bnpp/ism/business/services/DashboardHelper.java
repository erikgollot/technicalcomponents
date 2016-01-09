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
import com.bnpp.ism.entity.kpi.metadata.ComputedKpi;
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
		SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
		history.setKpiName(kpi.getName());
		history.setKpiCategory(kpi.getCategory());
		if (kpi instanceof ComputedKpi) {
			history.setComputed(true);
		}
		// Look for all snapshots of all application version
		for (ApplicationVersion version : versions) {
			if (version.getKpiSnapshots() != null) {
				for (ApplicationVersionKpiSnapshot snapshot : version
						.getKpiSnapshots()) {
					boolean computedFound = false;
					// First, look for computed Kpis
					if (snapshot.getComputedKpisValues() != null) {
						for (KpiValue v : snapshot.getComputedKpisValues()) {
							// Just get current Kpi, others will be taken in the
							// next call to createHistory
							if (v.getKpi() == kpi) {
								computedFound = true;
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
					// Measurements, only if computed have not be founded. Not
					// necessary if the current kpi has already been found
					// previously
					if (!computedFound
							&& snapshot.getManualMeasurements() != null) {
						float total = 0.0f;
						int num = 0;
						// Sum all measurements of all users, but only if value
						// is set
						for (ManualUserMeasurement measurement : snapshot
								.getManualMeasurements()) {

							for (KpiValue v : measurement.getValues()) {
								if (v.getKpi() == kpi && v.isSet()) {
									num++;
									total += v.getValue();
								}
							}

						}
						// add only if we've at least one value. It's possible
						// to have some measurements that does not contains all
						// kpi value because the person is just in charge of
						// defining part of the kpis.
						if (num != 0) {
							total = total / num;
							// Now adjust value...reason is for ENUM Kpi that
							// needs to give only literals
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
		if (history.getValues() != null) {
			Collections.sort(history.getValues(),
					new Comparator<DateKpiValue>() {
						@Override
						public int compare(DateKpiValue o1, DateKpiValue o2) {
							return o1.getDate().compareTo(o2.getDate());
						}

					});
		}
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
