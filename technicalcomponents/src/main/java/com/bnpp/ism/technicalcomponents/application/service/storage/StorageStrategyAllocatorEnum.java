package com.bnpp.ism.technicalcomponents.application.service.storage;

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
