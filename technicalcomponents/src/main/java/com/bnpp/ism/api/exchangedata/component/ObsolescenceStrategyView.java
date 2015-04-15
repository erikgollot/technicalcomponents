package com.bnpp.ism.api.exchangedata.component;

import java.io.Serializable;

public class ObsolescenceStrategyView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8474708034380383905L;

	private Long id;

	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	private String name;

	private int deprecatedNumberOfMonthBeforeUnavailability = 24;

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

}
