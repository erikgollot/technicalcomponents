package com.bnpp.ism.api;

public enum StorageStrategyAllocatorEnum {
	ORDERED("ordered"), RANDOM("random");

	private final String text;

	/**
	 * @param text
	 */
	private StorageStrategyAllocatorEnum(final String text) {
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
