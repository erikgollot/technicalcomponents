package com.bnpp.ism.technicalcomponents.application.model.application;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
public class ApplicationVersion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Version
	private Long version;
	@Column
	private String name;

	@OneToOne
	private BuiltOn builtOn;

	@OneToOne
	private CanRunOn canRunOn;	

	public boolean isAvailablePeriod() {
		if (getBuiltOn() != null) {
			return getBuiltOn().isAvailablePeriod();
		} else {
			return true;
		}
	}

	public boolean isWarningPeriod() {
		if (getBuiltOn() != null) {
			return getBuiltOn().isWarningPeriod();
		} else {
			return false;
		}
	}

	public boolean isHotPeriod() {
		if (getBuiltOn() != null) {
			return getBuiltOn().isHotPeriod();
		} else {
			return false;
		}
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BuiltOn getBuiltOn() {
		return builtOn;
	}

	public void setBuiltOn(BuiltOn builtOn) {
		this.builtOn = builtOn;
	}

	public CanRunOn getCanRunOn() {
		return canRunOn;
	}

	public void setCanRunOn(CanRunOn canRunOn) {
		this.canRunOn = canRunOn;
	}	
}
