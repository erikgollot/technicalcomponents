package com.bnpp.ism.technicalcomponents.application.serviceimpl.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bnpp.ism.technicalcomponents.application.dao.storage.DefaultStorageSetDao;
import com.bnpp.ism.technicalcomponents.application.dao.storage.StorageDao;
import com.bnpp.ism.technicalcomponents.application.dao.storage.StoredFileDao;
import com.bnpp.ism.technicalcomponents.application.dao.storage.StoredFileVersionDao;
import com.bnpp.ism.technicalcomponents.application.model.storage.DefaultStorageSet;
import com.bnpp.ism.technicalcomponents.application.model.storage.Storage;
import com.bnpp.ism.technicalcomponents.application.model.storage.StorageSet;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFile;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFileVersion;
import com.bnpp.ism.technicalcomponents.application.model.view.storage.StorageView;
import com.bnpp.ism.technicalcomponents.application.service.storage.DefaultStorageSetManager;
import com.bnpp.ism.technicalcomponents.application.service.storage.StorageException;
import com.fasterxml.uuid.Generators;

@Service
public class DefaultStorageSetManagerImpl implements DefaultStorageSetManager {
	@Autowired
	StoredFileDao fileDao;
	@Autowired
	StoredFileVersionDao fileVersionDao;
	@Autowired
	DefaultStorageSetDao dao;
	@Autowired
	private StorageDao storageDao;

	@Autowired
	Mapper dozerBeanMapper;

	@Transactional
	@Override
	public void removeFile(StoredFile image) {
		// remove each version
		if (image.getVersions() != null) {
			removePhysicalVersions(image);
			image.getVersions().clear();
			fileDao.delete(image);
		}
	}
	
	public void removeFileOnlyInDatabase(StoredFile image) {
		// remove each version
		if (image.getVersions() != null) {			
			image.getVersions().clear();
			fileDao.delete(image);
		}
	}

	public void removePhysicalVersions(StoredFile image) {
		for (StoredFileVersion version : image.getVersions()) {
			removePhysicalVersion(version);
		}
	}

	private void removePhysicalVersion(StoredFileVersion version) {
		fileVersionDao.delete(version);
		FileUtils.deleteQuietly(new File(version.getFullName()));
	}

	@Transactional
	@Override
	public StoredFile store(String name, byte[] content) {

		StorageSet storageSet = getDefault();
		if (storageSet != null) {
			String extension = name.substring(name.lastIndexOf(".") + 1);

			// Generate unique filename
			UUID uuid = Generators.timeBasedGenerator().generate();
			String physicalFilename = uuid.toString() + "." + extension;

			int hashcode = physicalFilename.hashCode();
			int mask = 255;
			int firstDir = hashcode & mask;
			int secondDir = (hashcode >> 8) & mask;
			int thridDir = (hashcode >> 8 >> 8) & mask;

			StringBuilder sb = new StringBuilder(File.separator);
			sb.append(String.format("%02x", firstDir));
			sb.append("/");
			sb.append(String.format("%02x", secondDir));
			sb.append("/");
			sb.append(String.format("%02x", thridDir));

			String physicalDirectory = sb.toString();

			Storage storage = storageSet.findBest(new Long(content.length));

			String fullStoragePhysicalDirectory = storage.getRootDir() + "/"
					+ physicalDirectory;
			File fdFullStoragePhysicalDirectory = new File(
					fullStoragePhysicalDirectory);

			try {
				FileUtils.forceMkdir(fdFullStoragePhysicalDirectory);
				String pathUnderRoot = fullStoragePhysicalDirectory + "/"
						+ physicalFilename;
				File fd = new File(pathUnderRoot);

				// Write content
				FileOutputStream fop = new FileOutputStream(fd);
				fop.write(content);
				fop.flush();
				fop.close();

				// now create StoredFile
				StoredFile storedfile = new StoredFile();
				storedfile.setFileType(extension);
				storedfile.setName(name);
				// And first version
				StoredFileVersion version = new StoredFileVersion();
				version.setRootDirectory(storage.getRootDir());
				version.setFilePathUnderDirectory(physicalDirectory + "/"
						+ physicalFilename);
				version.setVersion(1L);
				version.setMediaType(Files.probeContentType(fd.toPath()));
				storedfile.addVersion(version);
				version.setFile(storedfile);

				// save it
				fileDao.save(storedfile);
				fileVersionDao.save(version);
				return storedfile;

			} catch (IOException e) {
				StorageException ex = new StorageException();
				ex.setMessage("Cannot create file in directory "
						+ storage.getRootDir());
				throw ex;
			}
		} else {
			return null;

		}
	}

	@Transactional
	@Override
	public StoredFileVersion getStoredFileVersion(Long id) {
		return fileVersionDao.findOne(id);
	}

	@Transactional
	@Override
	public List<StorageView> getStorages() {
		StorageSet storageSet = getDefault();
		List<StorageView> ret = new ArrayList<StorageView>();
		if (storageSet.getStorages() != null) {
			for (Storage s : storageSet.getStorages()) {
				ret.add(dozerBeanMapper.map(s, StorageView.class));
			}
		}
		return ret;
	}

	@Override
	public StorageSet getDefault() {
		Iterable<DefaultStorageSet> storages = dao.findAll();
		if (storages.iterator().hasNext()) {
			StorageSet storageSet = storages.iterator().next();
			return storageSet;
		}
		return null;
	}

	@Transactional
	@Override
	public StorageView createNewStorage(StorageView newStorage) {

		DefaultStorageSet storageSet = (DefaultStorageSet) getDefault();
		if (storageSet != null) {
			// checkstorage
			checkStorage(newStorage, storageSet);
			Storage s = dozerBeanMapper.map(newStorage, Storage.class);
			// To be sure it's a new one...
			s.setId(null);
			s.setStorageSet(storageSet);
			storageSet.addStorage(s);
			storageDao.save(s);
			dao.save(storageSet);

			return dozerBeanMapper.map(s, StorageView.class);

		} else {
			StorageException ex = new StorageException();
			ex.setMessage("Strange, no default storage set defined");
			throw ex;
		}
	}

	private void checkStorage(StorageView newStorage,
			DefaultStorageSet storageSet) {
		if (newStorage.getRootDir() == null
				|| newStorage.getRootDir().length() == 0) {
			StorageException ex = new StorageException();
			ex.setMessage("Storage needs an no empty root directory");
			throw ex;
		}
		File fd = new File(newStorage.getRootDir());
		if (!fd.exists()) {
			// Try to create this directory
			if (!fd.mkdirs()) {
				StorageException ex = new StorageException();
				ex.setMessage("Storage directory cannot be created");
				throw ex;
			}
		}
		// No new storage "under existing" storage
		if (storageSet.getStorages() != null) {
			for (Storage existing : storageSet.getStorages()) {
				File parent = existing.getRootAsDirFile();
				File child = new File(newStorage.getRootDir());

				try {
					String parentPath = parent.getCanonicalPath().toString()+File.separator;
					String childPath = child.getCanonicalPath().toString();
					if (childPath.startsWith(parentPath)) {
						StorageException ex = new StorageException();
						ex.setMessage("Cannot create storage under an existing one ("
								+ parentPath + ")");
						throw ex;
					}
				} catch (IOException e) {
					// should not happen ??
				}
			}
		}
	}

	@Transactional
	@Override
	public void deleteStorage(Long storageId) {
		DefaultStorageSet storageSet = (DefaultStorageSet) getDefault();
		Storage s = null;
		if (storageSet != null
				&& (s = storageSet.getStorageFromPrimaryKey(storageId)) != null) {
			// We delete the storage even if they're files stored in it.
			// file retrieving is not impacted by the existance of the storage.
			// Removing a storage just mean that we can no longer store files in
			// this storage.
			// So it's important to NOT DELETE the rootDirectory of the storage.
			storageSet.removeStorage(s);
			s.setStorageSet(null);
			storageDao.delete(s);
			dao.save(storageSet);
		}
	}

}
