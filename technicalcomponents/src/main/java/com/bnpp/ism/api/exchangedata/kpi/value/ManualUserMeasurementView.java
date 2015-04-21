package com.bnpp.ism.api.exchangedata.kpi.value;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bnpp.ism.api.exchangedata.user.UserView;

public class ManualUserMeasurementView {

	Long id;

	Long version;

	UserView who;

	String creationDate;

	String comments;

	List<KpiValueView> values;

	public UserView getWho() {
		return who;
	}

	public void setWho(UserView who) {
		this.who = who;
	}

	

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public List<KpiValueView> getValues() {
		return values;
	}

	public void setValues(List<KpiValueView> values) {
		this.values = values;
	}

	public void addValue(KpiValueView v) {
		if (getValues() == null) {
			this.values = new ArrayList<KpiValueView>();
		}
		getValues().add(v);
	}

	public void removeValue(KpiValueView v) {
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

	public String getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}

	
}
