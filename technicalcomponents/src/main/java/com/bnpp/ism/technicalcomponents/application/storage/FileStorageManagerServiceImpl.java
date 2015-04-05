package com.bnpp.ism.technicalcomponents.application.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.technicalcomponents.application.dao.StoredFileDao;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFile;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFileVersion;
import com.bnpp.ism.technicalcomponents.application.service.FileStorageManagerService;

@Service
public class FileStorageManagerServiceImpl implements FileStorageManagerService {
	@Autowired
	private StoredFileDao dao;

	@Autowired
	Mapper dozerBeanMapper;

	/**
	 * context : a context string in which name must be unique
	 * 
	 * name : filename to store content : content of the file
	 * 
	 * force : si force est à true et que le fichier existe, on ne fait que
	 * remplacer la dernière version. si à false...ben on force rien on ajoute.
	 * 
	 * return : unique generated id of the file in the store.
	 */
	@Override
	@Transactional
	public String store(String context, String name, byte[] content,
			boolean force) {
		String filename = context + '/' + name;
		String extension = "txt";
		String nameWithoutExtension = name;
		int i = name.lastIndexOf('.');
		if (i > 0) {
			extension = name.substring(i+1);
			nameWithoutExtension = name.substring(0,i);
		}
		
		int hashcode = filename.hashCode();
		int mask = 255;
		int firstDir = hashcode & mask;
		int secondDir = (hashcode >> 8) & mask;
		int thridDir = (hashcode >> 8 >> 8) & mask;

		StringBuilder sb = new StringBuilder(File.separator);
		sb.append(String.format("%02x", firstDir));
		sb.append(File.separator);
		sb.append(String.format("%02x", secondDir));
		sb.append(String.format("%02x", thridDir));
		sb.append(String.format("%02x", name));

		String uniqueStorageKey = sb.toString();

		StoredFile file = null;
		// Chercher si le fichier existe déjà dans ce contexte

		Long version = 1L;
		// S'il existe et si le contenu est différent, on va créer une version
		// N+1, sauf si force = true
		// S'il n'existe pas on va créer la première version
		if (file != null) {
			List<StoredFileVersion> allVersions = new ArrayList<StoredFileVersion>(
					file.getVersions());
			Collections.sort(allVersions, new Comparator<StoredFileVersion>() {

				@Override
				public int compare(StoredFileVersion o1, StoredFileVersion o2) {
					return o1.getVersion().compareTo(o2.getVersion());

				}
			});
			StoredFileVersion last = allVersions.get(allVersions.size() - 1);
			version = last.getVersion() + 1L;
		}
		
		StringBuffer filePathUnderDirectory  = new StringBuffer();
		filePathUnderDirectory.append(String.format("%02x", firstDir));
		filePathUnderDirectory.append(File.separator);
		filePathUnderDirectory.append(String.format("%02x", secondDir));
		filePathUnderDirectory.append(String.format("%02x", thridDir));
		filePathUnderDirectory.append(String.format("%02x", nameWithoutExtension));
		filePathUnderDirectory.append("__").append(version).append(".").append(extension);

		// Rechercher le storage qui possède de l'espace de stockage : demander
		// à la stratégie de faire cela

		// Si on ne trouve pas de place--> exception

		// Sinon, prendre le storage proposé
		// Fabriquer la storageKey
		// ajouter __x à la storage key pour avoir le nom physique de la version
		// x du fichier

		// Fabriquer la version x, l'attacher au storedfile (créé ou récupéré)
		// et faire le stockage

		return null;
	}
}
