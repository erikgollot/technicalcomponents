package com.bnpp.ism.api.exchangedata.kpi.metadata;

import java.util.List;

public class ManualEnumKpiView extends AbstractKpiView {

	List<KpiEnumLiteralView> literals;

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

	

	public void setLiterals(List<KpiEnumLiteralView> literals) {
		this.literals = literals;
	}
}
