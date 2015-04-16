package com.bnpp.ism.api.exchangedata.kpi.application;

import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;

public class ApplicationObsolescenceKpiView extends AbstractKpiView {

	public KpiKindEnum getKind() {
		return KpiKindEnum.COMPUTED_APPLICATION;
	}
}
