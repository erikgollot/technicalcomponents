package com.bnpp.ism.technicalcomponents.application.service.storage;

import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFile;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFileVersion;

public interface DefaultStorageSetManager {

	void removeFile(StoredFile image);

	StoredFile store(String name, byte[] content);

	StoredFileVersion getStoredFileVersion(Long id);

}
