package com.bnpp.ism.technicalcomponents.application.model.component;

import java.util.Calendar;
import java.util.Date;

public class DefaultObscolescenceStrategyCalculator implements
		ObsolescenceStrategyCalculator {
	public static final int START_WARNING_PERIOD = -36;
	public static final int START_HOT_PERIOD = -24;

	@Override
	public boolean isAvailablePeriod(ComponentVersionInfo info) {
		return determinePeriod(info, START_WARNING_PERIOD, START_HOT_PERIOD) == ObscolescencePeriodEnum.AVAILABLE;
	}

	@Override
	public boolean isWarningPeriod(ComponentVersionInfo info) {
		return determinePeriod(info, START_WARNING_PERIOD, START_HOT_PERIOD) == ObscolescencePeriodEnum.WARNING;
	}

	@Override
	public boolean isHotPeriod(ComponentVersionInfo info) {
		return determinePeriod(info, START_WARNING_PERIOD, START_HOT_PERIOD) == ObscolescencePeriodEnum.HOT;
	}

	public static ObscolescencePeriodEnum determinePeriod(
			ComponentVersionInfo info, int warningoffset, int hotoffset) {
		Date now = new Date();

		// Today, we're before avaliablilty date,...no problem
		if (info.getAvailableDate() != null
				&& info.getAvailableDate().after(now)) {
			return ObscolescencePeriodEnum.AVAILABLE;
		} else {
			// if deprecated is set, warning is between deprecated and before
			// hot
			if (info.getDeprecatedDate() != null) {
				if (info.getDeprecatedDate().after(now)) {
					return ObscolescencePeriodEnum.AVAILABLE;
				} else
				// If no unavailabilty date, we're in warning
				if (info.getUnavailableDate() == null) {
					return ObscolescencePeriodEnum.WARNING;
				} else {
					// We've deprecated and unvailability. Look if in hot period
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(info.getUnavailableDate());
					calendar.add(Calendar.MONTH, hotoffset);
					Date startHot = calendar.getTime();

					if (now.after(startHot)) {
						return ObscolescencePeriodEnum.HOT;
					} else {
						return ObscolescencePeriodEnum.WARNING;
					}
				}

			} else {
				if (info.getUnavailableDate() != null) {
					Calendar calendar = Calendar.getInstance();
					calendar.setTime(info.getUnavailableDate());
					calendar.add(Calendar.MONTH, hotoffset);
					Date startHot = calendar.getTime();

					calendar.setTime(info.getUnavailableDate());
					calendar.add(Calendar.MONTH, warningoffset);
					Date startWarn = calendar.getTime();

					if (now.before(startWarn)) {
						return ObscolescencePeriodEnum.AVAILABLE;
					} else if (now.after(startWarn) && now.before(startHot)) {
						return ObscolescencePeriodEnum.WARNING;
					} else {
						return ObscolescencePeriodEnum.HOT;
					}
				} else {
					return ObscolescencePeriodEnum.AVAILABLE;
				}
			}
		}
	}
}
