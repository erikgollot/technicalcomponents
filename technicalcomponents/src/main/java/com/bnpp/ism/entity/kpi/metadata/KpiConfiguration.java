package com.bnpp.ism.entity.kpi.metadata;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Version;

@Entity
public class KpiConfiguration {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Version
	Long version;
	@Column(columnDefinition = "VARCHAR(100)", unique = true)
	String name;
	@Lob
	@Basic(fetch = FetchType.LAZY)
	String description;

	@OneToMany(fetch = FetchType.EAGER, mappedBy = "configuration", cascade = CascadeType.ALL)
	List<KpiUsage> usages;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<KpiUsage> getUsages() {
		return usages;
	}

	public void setUsages(List<KpiUsage> usages) {
		this.usages = usages;
	}

	public void addUsage(KpiUsage usage) {
		if (getUsages() == null) {
			this.usages = new ArrayList<KpiUsage>();
		}
		getUsages().add(usage);
		usage.setConfiguration(this);
	}

	public void removeUsage(KpiUsage usage) {
		getUsages().remove(usage);
		usage.setConfiguration(null);
	}

	public KpiUsage getUsage(Long uid) {
		KpiUsage u = null;
		if (usages != null) {
			for (KpiUsage us : usages) {
				if (us.getKpi().getId().equals(uid)) {
					u = us;
					break;
				}
			}
		}
		return u;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}
