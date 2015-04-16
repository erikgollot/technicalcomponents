package com.bnpp.ism.entity.kpi.value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bnpp.ism.entity.user.User;

public class ManualUserMeasurement {
	Long id;
	Long version;
	User who;
	Date when;
	String comments;
	List<KpiValue> values;

	public User getWho() {
		return who;
	}

	public void setWho(User who) {
		this.who = who;
	}

	public Date getWhen() {
		return when;
	}

	public void setWhen(Date when) {
		this.when = when;
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
}
