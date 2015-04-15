package com.bnpp.ism.api.exchangedata.storage;

import java.io.Serializable;

public class SimpleStoredFileVersionView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7389430872059756302L;

	Long id;

	Long version;

	String rootDirectory;

	String filePathUnderDirectory;

	String mediaType;

	String name;
	
	Long parentFileId;

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

	public String getFullName() {
		return getRootDirectory() + "/" + getFilePathUnderDirectory();
	}

	public String getMediaType() {
		return mediaType;
	}

	public void setMediaType(String mediaType) {
		this.mediaType = mediaType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getParentFileId() {
		return parentFileId;
	}

	public void setParentFileId(Long parentFileId) {
		this.parentFileId = parentFileId;
	}
}
