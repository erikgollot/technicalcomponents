package com.bnpp.ism.technicalcomponents.application.model.component;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * ok period | Low warning period | High warning period
 * Available--------dnombu--
 * ---Deprecated-----------unombu---------------------Unavailable
 * 
 * @author erik
 * 
 */
@Entity
public class ObsolescenceStrategy implements ObsolescenceStrategyCalculator {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column
	private String name;

	@Column
	private int deprecatedNumberOfMonthBeforeUnavailability = 24;

	@Column
	private int unavailabiltyNumberOfMonthBeforeUnavailability = 12;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDeprecatedNumberOfMonthBeforeUnavailability() {
		return deprecatedNumberOfMonthBeforeUnavailability;
	}

	public void setDeprecatedNumberOfMonthBeforeUnavailability(
			int deprecatedNumberOfMonthBeforeUnavailability) {
		this.deprecatedNumberOfMonthBeforeUnavailability = deprecatedNumberOfMonthBeforeUnavailability;
	}

	public int getUnavailabiltyNumberOfMonthBeforeUnavailability() {
		return unavailabiltyNumberOfMonthBeforeUnavailability;
	}

	public void setUnavailabiltyNumberOfMonthBeforeUnavailability(
			int unavailabiltyNumberOfMonthBeforeUnavailability) {
		this.unavailabiltyNumberOfMonthBeforeUnavailability = unavailabiltyNumberOfMonthBeforeUnavailability;
	}

	@Override
	public boolean isAvailablePeriod(ComponentVersionInfo info) {
		return DefaultObscolescenceStrategyCalculator.determinePeriod(info,
				deprecatedNumberOfMonthBeforeUnavailability,
				unavailabiltyNumberOfMonthBeforeUnavailability) == ObscolescencePeriodEnum.AVAILABLE;
	}

	@Override
	public boolean isWarningPeriod(ComponentVersionInfo info) {
		return DefaultObscolescenceStrategyCalculator.determinePeriod(info,
				deprecatedNumberOfMonthBeforeUnavailability,
				unavailabiltyNumberOfMonthBeforeUnavailability) == ObscolescencePeriodEnum.WARNING;
	}

	@Override
	public boolean isHotPeriod(ComponentVersionInfo info) {
		return DefaultObscolescenceStrategyCalculator.determinePeriod(info,
				deprecatedNumberOfMonthBeforeUnavailability,
				unavailabiltyNumberOfMonthBeforeUnavailability) == ObscolescencePeriodEnum.HOT;
	}

}
