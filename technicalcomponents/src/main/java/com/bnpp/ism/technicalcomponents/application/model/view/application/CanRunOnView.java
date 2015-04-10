package com.bnpp.ism.technicalcomponents.application.model.view.application;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.bnpp.ism.technicalcomponents.application.model.view.component.TechnicalComponentView;

public class CanRunOnView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 397701261934236402L;

	private Long id;

	private Long version;

	List<TechnicalComponentView> components;

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
	
	public void addComponent(TechnicalComponentView c) {
		if (getComponents() == null) {
			this.components = new ArrayList<TechnicalComponentView>();
		}
		getComponents().add(c);
	}

	public void removeComponent(TechnicalComponentView c) {
		if (getComponents() != null)
			getComponents().remove(c);
	}

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

	public List<TechnicalComponentView> getComponents() {
		return components;
	}

	public void setComponents(List<TechnicalComponentView> components) {
		this.components = components;
	}
}
