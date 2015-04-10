package com.bnpp.ism.technicalcomponents.application.serviceimpl.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bnpp.ism.technicalcomponents.application.dao.storage.DefaultStorageSetDao;
import com.bnpp.ism.technicalcomponents.application.dao.storage.StoredFileDao;
import com.bnpp.ism.technicalcomponents.application.dao.storage.StoredFileVersionDao;
import com.bnpp.ism.technicalcomponents.application.model.storage.DefaultStorageSet;
import com.bnpp.ism.technicalcomponents.application.model.storage.Storage;
import com.bnpp.ism.technicalcomponents.application.model.storage.StorageSet;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFile;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFileVersion;
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

	@Transactional
	@Override
	public void removeFile(StoredFile image) {
		// remove each version
		if (image.getVersions() != null) {
			for (StoredFileVersion version : image.getVersions()) {
				removeVersion(version);
			}
			image.getVersions().clear();
			fileDao.delete(image);
		}
	}

	private void removeVersion(StoredFileVersion version) {
		fileVersionDao.delete(version);
		FileUtils.deleteQuietly(new File(version.getFullName()));
	}

	@Transactional
	@Override
	public StoredFile store(String name, byte[] content) {
		Iterable<DefaultStorageSet> storages = dao.findAll();
		if (storages.iterator().hasNext()) {
			StorageSet storageSet = storages.iterator().next();
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

				String fullStoragePhysicalDirectory = storage.getRootDir()
						+ "/" + physicalDirectory;
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
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public StoredFileVersion getStoredFileVersion(Long id) {
		return fileVersionDao.findOne(id);
	}
}
