package com.bnpp.ism.entity.kpi.application;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import com.bnpp.ism.entity.application.ApplicationVersion;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.ApplicationVersionComputedKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiEnum;
import com.bnpp.ism.entity.kpi.metadata.KpiEnumLiteral;
import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;
import com.bnpp.ism.entity.kpi.metadata.UnitEnum;

@Entity
@DiscriminatorValue("ApplicationObsolescenceKpi")
public class ApplicationObsolescenceKpi extends ApplicationVersionComputedKpi implements KpiEnum {
	public static final String MY_NAME = "Obsolescence de l'application";
	public static final String MY_SHORT_NAME = "KC2d";

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
	public String getShortName() {
		return MY_SHORT_NAME;
	}
	
	public void setShortName(String name) {
		super.setName(MY_SHORT_NAME);
	}

	@Override
	public String getName() {
		return MY_NAME;
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
		return compute((ApplicationVersion) context);
	}

	@Override
	public KpiKindEnum getKind() {
		return KpiKindEnum.COMPUTED_APPLICATION_VERSION;
	}

	@Override
	public void setUnit(String unit) {
	}

	@Override
	public String getUnit() {
		return UnitEnum.NONE.toString();
	}

	@Override
	public void setKind(KpiKindEnum kind) {
		// FORCE KpiKindEnum.COMPUTED_APPLICATION_VERSION
		super.setKind(KpiKindEnum.COMPUTED_APPLICATION_VERSION);
	}

	@Override
	public boolean canCompute(Object context) {
		return canCompute((ApplicationVersion)context);
	}

	@Override
	public boolean canCompute(ApplicationVersion applicationVersion) {
		if (applicationVersion.getBuiltOn() != null
				&& applicationVersion.getBuiltOn().getComponents() != null
				&& applicationVersion.getBuiltOn().getComponents().size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean acceptValue(float value) {
		return true;
	}

}
