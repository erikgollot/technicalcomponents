package com.bnpp.ism.entity.application;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Version;

import com.bnpp.ism.entity.component.TechnicalComponent;

@Entity
public class BuiltOn {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Version
	private Long version;

	@ManyToMany
	List<TechnicalComponent> components;

	public void addComponent(TechnicalComponent c) {
		if (getComponents() == null) {
			this.components = new ArrayList<TechnicalComponent>();
		}
		getComponents().add(c);
	}

	public void removeComponent(TechnicalComponent c) {
		if (getComponents() != null)
			getComponents().remove(c);
	}
	
	public void removeAllComponents() {
		if (getComponents()!=null) {
			getComponents().clear();
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

	public List<TechnicalComponent> getComponents() {
		return components;
	}

	public void setComponents(List<TechnicalComponent> components) {
		this.components = components;
	}

	public boolean isAvailablePeriod() {
		if (getComponents() != null) {
			for (TechnicalComponent c : getComponents()) {
				if (!c.isAvailablePeriod())
					return false;
			}
			return true;
		} else {
			return true;
		}
	}

	public boolean isWarningPeriod() {
		boolean warning = false;
		boolean hot = false;
		if (getComponents() != null) {
			for (TechnicalComponent c : getComponents()) {
				if (c.isWarningPeriod()) {
					warning = true;
				}
				if (c.isHotPeriod()) {
					hot = true;
				}
			}
			if (hot)
				return false;
			else
				return warning;
		} else {
			return false;
		}
	}

	public boolean isHotPeriod() {
		if (getComponents() != null) {
			for (TechnicalComponent c : getComponents()) {
				if (c.isHotPeriod()) {
					return true;
				}
			}
			return false;
		} else {
			return false;
		}
	}

	@Override
	protected Object clone() throws CloneNotSupportedException {
		BuiltOn cloned = new BuiltOn();

		// On laisse l'id et la version à null
		// On copie les composants sans les cloner eux-mêmes car ceux sont les
		// mêmes dans le clone
		List<TechnicalComponent> comps = new ArrayList<TechnicalComponent>();
		comps.addAll(getComponents());
		cloned.setComponents(comps);

		return cloned;
	}
}
