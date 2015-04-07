package com.bnpp.ism.technicalcomponents.application.mvc.storage;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFileVersion;
import com.bnpp.ism.technicalcomponents.application.service.FileStorageManagerService;

@RestController
public class FileStorageController {

	@Autowired
	FileStorageManagerService storageManager;

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
}
