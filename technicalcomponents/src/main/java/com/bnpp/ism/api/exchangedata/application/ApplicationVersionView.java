package com.bnpp.ism.api.exchangedata.application;

import java.io.Serializable;

public class ApplicationVersionView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1012506178511265828L;

	private Long id;
	private Long applicationId;

	private Long version;

	private String name;

	private BuiltOnView builtOn;

	private CanRunOnView canRunOn;

	boolean isAvailablePeriod;
	boolean isWarningPeriod;
	boolean isHotPeriod;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BuiltOnView getBuiltOn() {
		return builtOn;
	}

	public void setBuiltOn(BuiltOnView builtOn) {
		this.builtOn = builtOn;
	}

	public CanRunOnView getCanRunOn() {
		return canRunOn;
	}

	public void setCanRunOn(CanRunOnView canRunOn) {
		this.canRunOn = canRunOn;
	}

	public boolean isAvailablePeriod() {
		return isAvailablePeriod;
	}

	public void setAvailablePeriod(boolean isAvailablePeriod) {
		this.isAvailablePeriod = isAvailablePeriod;
	}

	public boolean isWarningPeriod() {
		return isWarningPeriod;
	}

	public void setWarningPeriod(boolean isWarningPeriod) {
		this.isWarningPeriod = isWarningPeriod;
	}

	public boolean isHotPeriod() {
		return isHotPeriod;
	}

	public void setHotPeriod(boolean isHotPeriod) {
		this.isHotPeriod = isHotPeriod;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public Long getApplicationId() {
		return applicationId;
	}

	public void setApplicationId(Long applicationId) {
		this.applicationId = applicationId;
	}
}
