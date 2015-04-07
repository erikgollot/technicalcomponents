package com.bnpp.ism.technicalcomponents.application.model.storage;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;

import com.bnpp.ism.technicalcomponents.application.dao.DefaultStorageSetDao;
import com.bnpp.ism.technicalcomponents.application.dao.StoredFileDao;
import com.bnpp.ism.technicalcomponents.application.dao.StoredFileVersionDao;
import com.bnpp.ism.technicalcomponents.application.service.StorageException;
import com.fasterxml.uuid.Generators;

@Configurable
public class StorageManager {

	@Autowired
	DefaultStorageSetDao dao;

	@Autowired
	StoredFileDao fileDao;

	@Autowired
	StoredFileVersionDao fileVersionDao;

	public StoredFile store(String name, byte[] content) {
		StorageSet storageSet = dao.findOne(1L);
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

				// save it
				fileDao.save(storedfile);
				fileVersionDao.save(version);

			} catch (IOException e) {
				StorageException ex = new StorageException();
				ex.setMessage("Cannot create file in directory "
						+ storage.getRootDir());
				throw ex;
			}
		}
		return null;
	}

	public StoredFileVersion getStoredFileVersion(Long id) {
		return fileVersionDao.findOne(id);
	}
}
