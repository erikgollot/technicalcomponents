package com.bnpp.ism.api.exchangedata.application;

import java.util.ArrayList;
import java.util.List;

public class ApplicationKpiHistory {
	String kpiName;
	String kpiCategory;
	List<DateKpiValue> values;

	public String getKpiCategory() {
		return kpiCategory;
	}

	public void setKpiCategory(String kpiCategory) {
		this.kpiCategory = kpiCategory;
	}

	public void addValue(DateKpiValue v) {
		if (values == null) {
			values = new ArrayList<DateKpiValue>();
		}
		getValues().add(v);
	}
	public void removeValue(DateKpiValue v) {		
		getValues().remove(v);
	}

	public String getKpiName() {
		return kpiName;
	}

	public void setKpiName(String kpiName) {
		this.kpiName = kpiName;
	}

	public List<DateKpiValue> getValues() {
		return values;
	}

	public void setValues(List<DateKpiValue> values) {
		this.values = values;
	}
}
