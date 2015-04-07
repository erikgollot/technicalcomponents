package com.bnpp.ism.technicalcomponents.application.service;

import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFileVersion;

public interface FileStorageManagerService {
	String store(String name, byte[] content);

	StoredFileVersion getStoredFileVersion(Long id);
}
