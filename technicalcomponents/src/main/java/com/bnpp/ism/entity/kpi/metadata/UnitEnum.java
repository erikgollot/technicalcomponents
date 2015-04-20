package com.bnpp.ism.entity.kpi.metadata;

public enum UnitEnum {
	NONE("NONE"), MINUTE("MINUTE"), HOUR("HOUR"), DAY("DAY"), WEEK("WEEK"), MONTH(
			"MONTH"), YEAR("YEAR"), ISSUE("ISSUE");
	private final String text;

	/**
	 * @param text
	 */
	private UnitEnum(final String text) {
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
