package com.bnpp.ism.api.exchangedata.application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationKpiDashboard {
	List<ApplicationKpiHistory> kpiHistories;

	public List<ApplicationKpiHistory> getKpiHistories() {
		return kpiHistories;
	}

	public void setKpiHistories(List<ApplicationKpiHistory> kpiHistories) {
		this.kpiHistories = kpiHistories;
	}

	public void addKpiHistory(ApplicationKpiHistory kpiHistory) {
		if (kpiHistories == null) {
			kpiHistories = new ArrayList<ApplicationKpiHistory>();
		}
		getKpiHistories().add(kpiHistory);
	}

	public void removeKpiHistory(ApplicationKpiHistory kpiHistory) {
		getKpiHistories().remove(kpiHistory);
	}
}
