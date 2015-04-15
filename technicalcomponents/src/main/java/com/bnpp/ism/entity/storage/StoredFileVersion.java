package com.bnpp.ism.entity.storage;

import java.io.File;
import java.io.IOException;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import org.apache.commons.io.FileUtils;

@Entity
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

	@ManyToOne(fetch = FetchType.LAZY)
	StoredFile file;

	@Column
	String mediaType;
	@Version
	private Long persistantVersion;

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

	public StoredFile getFile() {
		return file;
	}

	public void setFile(StoredFile file) {
		this.file = file;
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

	public byte[] getContentBytes() {
		File file = new File(getFullName());

		try {
			return FileUtils.readFileToByteArray(file);
		} catch (IOException e) {
			return null;
		}
	}

	public Long getPersitantVersion() {
		return persistantVersion;
	}

	public void setPersitantVersion(Long persitantVersion) {
		this.persistantVersion = persitantVersion;
	}
}
