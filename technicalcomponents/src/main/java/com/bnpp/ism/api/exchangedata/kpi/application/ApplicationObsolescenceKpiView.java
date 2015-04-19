package com.bnpp.ism.api.exchangedata.kpi.application;

import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;

public class ApplicationObsolescenceKpiView extends AbstractKpiView {
	String clazz = "com.bnpp.ism.api.exchangedata.kpi.application.ApplicationObsolescenceKpiView";
	public KpiKindEnum getKind() {
		return KpiKindEnum.COMPUTED_APPLICATION;
	}
	public String getClazz() {
		return clazz;
	}
	public void setClazz(String clazz) {
		this.clazz = clazz;
	}
}
