package com.bnpp.ism.technicalcomponents.application.model.storage;

import java.io.File;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.bnpp.ism.technicalcomponents.application.service.storage.StorageException;

@Entity
public class Storage {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column
	private String rootDir;
	@Column
	private Integer quota = 100;
	@Column
	private boolean active = true;
	@Column
	private int orderInSet;

	@ManyToOne(fetch=FetchType.LAZY)
	DefaultStorageSet storageSet;
	
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
		if (quota < 0 || quota > 100) {
			StorageException ex = new StorageException();
			ex.setMessage("Quota must be between 0 and 100");
			throw ex;
		}
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
		File root = new File(getRootDir());
		return root.getUsableSpace() * (100/getQuota());
	}
	
	public File getRootAsDirFile() {
		return new File(getRootDir());
	}
}
