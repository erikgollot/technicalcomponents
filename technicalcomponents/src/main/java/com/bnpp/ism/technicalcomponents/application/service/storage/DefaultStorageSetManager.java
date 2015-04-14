package com.bnpp.ism.technicalcomponents.application.service.storage;

import java.util.List;

import com.bnpp.ism.technicalcomponents.application.model.storage.StorageSet;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFile;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFileVersion;
import com.bnpp.ism.technicalcomponents.application.model.view.storage.StorageView;

public interface DefaultStorageSetManager {

	void removeFile(StoredFile image);
	void removeFileOnlyInDatabase(StoredFile image);

	 void removePhysicalVersions(StoredFile image);

	StoredFile store(String name, byte[] content);

	StoredFileVersion getStoredFileVersion(Long id);

	List<StorageView> getStorages();

	StorageView createNewStorage(StorageView newStorage);

	void deleteStorage(Long storageId);

	StorageSet getDefault();
}
