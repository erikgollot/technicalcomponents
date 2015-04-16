package com.bnpp.ism.api.exchangedata.kpi.metadata;

import java.util.List;

public class KpiConfigurationView {

	Long id;

	Long version;

	String name;

	List<KpiUsageView> usages;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<KpiUsageView> getUsages() {
		return usages;
	}

	public void setUsages(List<KpiUsageView> usages) {
		this.usages = usages;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
}
