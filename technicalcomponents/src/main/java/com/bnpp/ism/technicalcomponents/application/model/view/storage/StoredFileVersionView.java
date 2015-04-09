package com.bnpp.ism.technicalcomponents.application.model.view.storage;

import java.io.Serializable;

public class StoredFileVersionView implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7389430872059756302L;

	Long id;

	Long version;

	String rootDirectory;

	String filePathUnderDirectory;

	String mediaType;

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
}
