package com.bnpp.ism.entity.kpi.value;

import java.util.ArrayList;
import java.util.Date;
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
import javax.persistence.Version;

import com.bnpp.ism.entity.user.User;

@Entity
public class ManualUserMeasurement {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	Long id;
	@Version
	Long version;

	@ManyToOne
	User who;

	@Column
	Date creationDate;
	@Column(length = 1000)
	String comments;
	@OneToMany(cascade = CascadeType.ALL)
	List<KpiValue> values;

	@ManyToOne(fetch = FetchType.LAZY)
	ApplicationVersionKpiSnapshot snapshot;

	public User getWho() {
		return who;
	}

	public void setWho(User who) {
		this.who = who;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<KpiValue> getValues() {
		return values;
	}

	public void setValues(List<KpiValue> values) {
		this.values = values;
	}

	public void addValue(KpiValue v) {
		if (getValues() == null) {
			this.values = new ArrayList<KpiValue>();
		}
		getValues().add(v);
	}

	public void removeValue(KpiValue v) {
		getValues().remove(v);
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

	public ApplicationVersionKpiSnapshot getSnapshot() {
		return snapshot;
	}

	public void setSnapshot(ApplicationVersionKpiSnapshot snapshot) {
		this.snapshot = snapshot;
	}

	public Date getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
}
