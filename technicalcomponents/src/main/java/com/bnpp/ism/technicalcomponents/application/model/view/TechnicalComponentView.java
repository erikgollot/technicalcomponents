package com.bnpp.ism.technicalcomponents.application.model.view;

import java.io.Serializable;

public class TechnicalComponentView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6470667767638775042L;
	private Long id;

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
}
