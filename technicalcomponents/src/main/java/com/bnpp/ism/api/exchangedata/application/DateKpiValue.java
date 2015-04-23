package com.bnpp.ism.api.exchangedata.application;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class DateKpiValue {
	float value;
	String valueAsString;
	String dateStr;
	@JsonIgnore
	Date date;
	String applicationVersion;

	public float getValue() {
		return value;
	}

	public void setValue(float value) {
		this.value = value;
	}

	public String getValueAsString() {
		return valueAsString;
	}

	public void setValueAsString(String valueAsString) {
		this.valueAsString = valueAsString;
	}

	public String getApplicationVersion() {
		return applicationVersion;
	}

	public void setApplicationVersion(String applicationVersion) {
		this.applicationVersion = applicationVersion;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getDateStr() {
		return dateStr;
	}

	public void setDateStr(String dateStr) {
		this.dateStr = dateStr;
	}

	public Date getDate() {
		return date;
	}
}
