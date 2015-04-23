package com.bnpp.ism.entity.application;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

import com.bnpp.ism.entity.component.TechnicalComponent;
import com.bnpp.ism.entity.kpi.value.ApplicationVersionKpiSnapshot;

@Entity
public class ApplicationVersion {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Version
	private Long version;
	@Column
	private String name;

	@ManyToOne(fetch=FetchType.LAZY)
	Application application;
	
	@OneToOne(cascade = CascadeType.ALL)
	private BuiltOn builtOn;

	@OneToOne(cascade = CascadeType.ALL)
	private CanRunOn canRunOn;

	@OneToMany(mappedBy = "applicationVersion", cascade = CascadeType.ALL)
	List<ApplicationVersionKpiSnapshot> kpiSnapshots;

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

	public void addBuiltOnComponent(TechnicalComponent component) {
		if (getBuiltOn() == null) {
			setBuiltOn(new BuiltOn());
		}
		getBuiltOn().addComponent(component);
	}

	public void addCanRunOnComponent(TechnicalComponent component) {
		if (getCanRunOn() == null) {
			setCanRunOn(new CanRunOn());
		}
		getCanRunOn().addComponent(component);
	}

	public void addKpiSnapshot(ApplicationVersionKpiSnapshot s) {
		if (getKpiSnapshots() == null) {
			this.kpiSnapshots = new ArrayList<ApplicationVersionKpiSnapshot>();
		}
		getKpiSnapshots().add(s);
		s.setApplicationVersion(this);
	}

	public void removeKpiSnapshot(ApplicationVersionKpiSnapshot s) {
		getKpiSnapshots().remove(s);
	}

	public List<ApplicationVersionKpiSnapshot> getKpiSnapshots() {
		return kpiSnapshots;
	}

	public void setKpiSnapshots(List<ApplicationVersionKpiSnapshot> kpiSnapshots) {
		this.kpiSnapshots = kpiSnapshots;
	}

	public Application getApplication() {
		return application;
	}

	public void setApplication(Application application) {
		this.application = application;
	}
}
