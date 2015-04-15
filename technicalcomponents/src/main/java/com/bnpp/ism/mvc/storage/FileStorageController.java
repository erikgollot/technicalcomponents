package com.bnpp.ism.mvc.storage;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bnpp.ism.api.IDefaultStorageSetManager;
import com.bnpp.ism.api.exchangedata.storage.StorageView;
import com.bnpp.ism.entity.storage.StoredFileVersion;

@RestController
public class FileStorageController {

	@Autowired
	IDefaultStorageSetManager storageManager;

	@RequestMapping(value = "/service/storeFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFile(@RequestParam("file") MultipartFile file) {

		String generatedFileId = null;

		if (file != null && file.getOriginalFilename() != null
				&& file.getOriginalFilename().length() != 0 && !file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				storageManager.store(file.getOriginalFilename(), bytes);
				return file.getOriginalFilename();

			} catch (IOException e) {
				return null;
			}

		}

		return generatedFileId;
	}

	@RequestMapping(value = "/service/file/{id}", method = RequestMethod.GET)
	@ResponseBody
	public byte[] getFile(@PathVariable String id) {

		StoredFileVersion file = storageManager.getStoredFileVersion(new Long(
				id));
		if (file != null) {
			return file.getContentBytes();
		} else
			return null;
	}

	@RequestMapping(value = "/service/storages", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<StorageView> getStorages() {
		return storageManager.getStorages();
	}

	@RequestMapping(value = "/service/createNewStore", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	StorageView createNewStore(@RequestBody StorageView s) {
		return storageManager.createNewStorage(s);
	}

	@RequestMapping(value = "/service/deleteStorage/{idStorage}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteStorage(@PathVariable("idStorage") Long idStorage) {
		storageManager.deleteStorage(idStorage);
	}
}
