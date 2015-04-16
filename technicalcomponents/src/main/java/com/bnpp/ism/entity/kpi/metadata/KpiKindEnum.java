package com.bnpp.ism.entity.kpi.metadata;

public enum KpiKindEnum {
	MANUAL("MANUAL"), COMPUTED_APPLICATION("COMPUTED_APPLICATION"), COMPUTED_PROJECT(
			"COMPUTED_PROJECT");
	private final String text;

	/**
	 * @param text
	 */
	private KpiKindEnum(final String text) {
		this.text = text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Enum#toString()
	 */
	@Override
	public String toString() {
		return text;
	}
}
