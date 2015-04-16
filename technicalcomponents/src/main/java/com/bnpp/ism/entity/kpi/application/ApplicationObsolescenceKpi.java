package com.bnpp.ism.entity.kpi.application;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bnpp.ism.entity.application.ApplicationVersion;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.ApplicationComputedKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiEnum;
import com.bnpp.ism.entity.kpi.metadata.KpiEnumLiteral;
import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;

@Entity
@DiscriminatorValue("ApplicationObsolescenceKpi")
public class ApplicationObsolescenceKpi extends AbstractKpi implements KpiEnum,
		ApplicationComputedKpi {
	public static final String MY_NAME = "Application Obscolescence";

	@Override
	public float compute(ApplicationVersion applicationVersion) {
		if (applicationVersion.isAvailablePeriod())
			return 1.0f;
		else if (applicationVersion.isWarningPeriod())
			return 2.0f;
		else
			return 3.0f;
	}

	@Override
	public float adjustValue(float value) {
		return value;
	}

	
	public void setName(String name) {
		super.setName(MY_NAME);
	}

	@Override
	public KpiEnumLiteral getLiteralWithValue(float value) {
		if (1.0f == value) {
			return new ApplicationObsolescenceEnumLiteralAvailable();
		} else if (2.0f == value) {
			return new ApplicationObsolescenceEnumLiteralWarning();
		} else {
			return new ApplicationObsolescenceEnumLiteralHot();
		}
	}

	@Override
	public float compute(Object context) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public KpiKindEnum getKind() {
		return KpiKindEnum.COMPUTED_APPLICATION;
	}
}
