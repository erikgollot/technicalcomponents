package com.bnpp.ism.api.exchangedata.storage;

import java.io.Serializable;

public class StorageView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -150475404249303304L;

	private Long id;

	private String rootDir;

	private Integer quota = 100;

	private boolean active = true;

	private int orderInSet;

	private Long version;

	private Long availableDiskSpace;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRootDir() {
		return rootDir;
	}

	public void setRootDir(String rootDir) {
		this.rootDir = rootDir;
	}

	public Integer getQuota() {
		return quota;
	}

	public void setQuota(Integer quota) {

		this.quota = quota;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getOrderInSet() {
		return orderInSet;
	}

	public void setOrderInSet(int order) {
		this.orderInSet = order;
	}

	public Long getAvailableDiskSpace() {
		return availableDiskSpace;
	}

	public void setAvailableDiskSpace(Long availableDiskSpace) {
		this.availableDiskSpace = availableDiskSpace;
	}

}
