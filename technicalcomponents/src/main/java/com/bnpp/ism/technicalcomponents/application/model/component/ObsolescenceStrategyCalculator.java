package com.bnpp.ism.technicalcomponents.application.model.component;

public interface ObsolescenceStrategyCalculator {

	boolean isAvailablePeriod(ComponentVersionInfo info);

	boolean isWarningPeriod(ComponentVersionInfo info);

	boolean isHotPeriod(ComponentVersionInfo info);

}
