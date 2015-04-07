package com.bnpp.ism.technicalcomponents.application.storage;

import javax.transaction.Transactional;
import javax.transaction.Transactional.TxType;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.technicalcomponents.application.dao.StoredFileDao;
import com.bnpp.ism.technicalcomponents.application.model.storage.StorageManager;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFile;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFileVersion;
import com.bnpp.ism.technicalcomponents.application.service.FileStorageManagerService;

@Service
public class FileStorageManagerServiceImpl implements FileStorageManagerService {
	@Autowired
	private StoredFileDao dao;

	@Autowired
	Mapper dozerBeanMapper;

	@Autowired
	StorageManager storageManager;

	/**
	 * 
	 * name : filename to store content : content of the file
	 * 
	 * force : si force est à true et que le fichier existe, on ne fait que
	 * remplacer la dernière version. si à false...ben on force rien on ajoute.
	 * 
	 * return : unique generated id of the file in the store.
	 */
	@Override
	@Transactional(value=TxType.REQUIRED)
	public String store(String name, byte[] content) {
		StoredFile f = storageManager.store(name, content);
		if (f != null) {
			return f.getId().toString();
		} else {
			return null;
		}
	}

	@Override
	@Transactional(value=TxType.REQUIRED)
	public StoredFileVersion getStoredFileVersion(Long id) {
		return storageManager.getStoredFileVersion(id);		
	}
}
