package com.bnpp.ism.api.exchangedata.kpi.metadata;

import java.util.List;

import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;

public class ManualEnumKpiView extends AbstractKpiView {

	List<KpiEnumLiteralView> literals;
	KpiKindEnum kind;

	public List<KpiEnumLiteralView> getLiterals() {
		return literals;
	}

	public KpiEnumLiteralView getLiteral(String uid) {
		KpiEnumLiteralView enumLiteral = null;
		if (getLiterals() != null) {
			for (KpiEnumLiteralView en : literals) {
				if (en.getId().equals(uid)) {
					enumLiteral = en;
					break;
				}
			}
		}
		return enumLiteral;
	}

	public KpiKindEnum getKind() {
		return KpiKindEnum.MANUAL;
	}

	public void setKind(KpiKindEnum kind) {
		this.kind = kind;
	}
}
