package com.bnpp.ism.technicalcomponents.application.model.view.component;

import java.io.Serializable;

import com.bnpp.ism.technicalcomponents.application.model.view.storage.StoredFileVersionView;

public class TechnicalComponentView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6470667767638775042L;
	private Long id;
	private Long version;
	
	ObsolescenceStrategyView obscolescenceStrategy;
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	private StoredFileVersionView image;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	ComponentVersionInfoView vendorInformations;

	public ComponentVersionInfoView getVendorInformations() {
		return vendorInformations;
	}

	public void setVendorInformations(
			ComponentVersionInfoView vendorInformations) {
		this.vendorInformations = vendorInformations;
	}

	ComponentVersionInfoView localInformations;

	public ComponentVersionInfoView getLocalInformations() {
		return localInformations;
	}

	public void setLocalInformations(ComponentVersionInfoView localInformations) {
		this.localInformations = localInformations;
	}

	private boolean forceObsolete = false;

	public boolean isForceObsolete() {
		return forceObsolete;
	}

	public void setForceObsolete(boolean forceObsolete) {
		this.forceObsolete = forceObsolete;
	}

	boolean isAvailablePeriod;
	boolean isWarningPeriod;
	boolean isHotPeriod;

	public boolean getIsAvailable() {
		return isAvailablePeriod();
	}

	public boolean isAvailablePeriod() {
		return isAvailablePeriod;
	}

	public void setAvailablePeriod(boolean isAvailablePeriod) {
		this.isAvailablePeriod = isAvailablePeriod;
	}

	public boolean getIsWarningPeriod() {
		return isWarningPeriod();
	}

	public boolean isWarningPeriod() {
		return isWarningPeriod;
	}

	public void setWarningPeriod(boolean isWarningPeriod) {
		this.isWarningPeriod = isWarningPeriod;
	}

	public boolean getIsHotPeriod() {
		return isHotPeriod();
	}

	public boolean isHotPeriod() {
		return isHotPeriod;
	}

	public void setHotPeriod(boolean isHotPeriod) {
		this.isHotPeriod = isHotPeriod;
	}

	public StoredFileVersionView getImage() {
		return image;
	}

	public void setImage(StoredFileVersionView image) {
		this.image = image;
	}

	public ObsolescenceStrategyView getObscolescenceStrategy() {
		return obscolescenceStrategy;
	}

	public void setObscolescenceStrategy(
			ObsolescenceStrategyView obscolescenceStrategy) {
		this.obscolescenceStrategy = obscolescenceStrategy;
	}

}
