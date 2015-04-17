package com.bnpp.ism.api.exchangedata.kpi.application;

import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class")
public class ApplicationObsolescenceKpiView extends AbstractKpiView {
	public KpiKindEnum getKind() {
		return KpiKindEnum.COMPUTED_APPLICATION;
	}
}
