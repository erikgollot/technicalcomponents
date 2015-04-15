package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.storage.StorageView;
import com.bnpp.ism.entity.storage.StorageSet;
import com.bnpp.ism.entity.storage.StoredFile;
import com.bnpp.ism.entity.storage.StoredFileVersion;

public interface IDefaultStorageSetManager {

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
