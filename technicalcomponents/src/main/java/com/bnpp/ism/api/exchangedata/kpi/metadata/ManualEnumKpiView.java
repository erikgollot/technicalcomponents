package com.bnpp.ism.api.exchangedata.kpi.metadata;

import java.util.List;

import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;

public class ManualEnumKpiView extends AbstractKpiView {
	String clazz = "com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView";
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

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}


	public void setLiterals(List<KpiEnumLiteralView> literals) {
		this.literals = literals;
	}
}
