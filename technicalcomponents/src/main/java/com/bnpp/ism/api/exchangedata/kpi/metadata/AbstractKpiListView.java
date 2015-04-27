package com.bnpp.ism.api.exchangedata.kpi.metadata;

import java.util.ArrayList;
import java.util.List;

public class AbstractKpiListView {
	List<AbstractKpiView> kpis;
	
	public void addKpi(AbstractKpiView kpi) {
		if (getKpis()==null) {
			kpis=new ArrayList<AbstractKpiView>();
		}
		getKpis().add(kpi);
	}
	
	public void removeKpi(AbstractKpiView kpi) {		
		getKpis().remove(kpi);
	}

	public List<AbstractKpiView> getKpis() {
		return kpis;
	}

	public void setKpis(List<AbstractKpiView> kpis) {
		this.kpis = kpis;
	}
}
