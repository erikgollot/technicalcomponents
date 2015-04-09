package com.bnpp.ism.technicalcomponents.application.model.view.component;

import java.io.Serializable;

public class ImageGallery implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -9221979121689570550L;
	String name;
	Long fileId;
	String fileType;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}
