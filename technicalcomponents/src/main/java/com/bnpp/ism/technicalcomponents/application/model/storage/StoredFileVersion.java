package com.bnpp.ism.technicalcomponents.application.model.storage;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class StoredFileVersion {
	@Column
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Long id;
	@Column
	Long version;
	@Column
	String rootDirectory;
	@Column
	String filePathUnderDirectory;

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

	public String getRootDirectory() {
		return rootDirectory;
	}

	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}

	public String getFilePathUnderDirectory() {
		return filePathUnderDirectory;
	}

	public void setFilePathUnderDirectory(String filePathUnderDirectory) {
		this.filePathUnderDirectory = filePathUnderDirectory;
	}

}
