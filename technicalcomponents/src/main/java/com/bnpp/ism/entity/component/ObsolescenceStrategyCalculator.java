package com.bnpp.ism.entity.component;

public interface ObsolescenceStrategyCalculator {

	boolean isAvailablePeriod(ComponentVersionInfo info);

	boolean isWarningPeriod(ComponentVersionInfo info);

	boolean isHotPeriod(ComponentVersionInfo info);

}
