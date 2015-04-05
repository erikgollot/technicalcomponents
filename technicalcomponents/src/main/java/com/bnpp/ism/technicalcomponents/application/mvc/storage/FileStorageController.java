package com.bnpp.ism.technicalcomponents.application.mvc.storage;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bnpp.ism.technicalcomponents.application.service.FileStorageManagerService;

@RestController
public class FileStorageController {
	
	@Autowired
	FileStorageManagerService storageManager;
	
	@RequestMapping(value = "/storeFile", method = RequestMethod.POST)
	public @ResponseBody
	String uploadFile(@RequestParam("context") String context,@RequestParam("name") String name,
			@RequestParam("file") MultipartFile file,@RequestParam("force") boolean force) {
		
		String generatedFileId = null;
		
		if (name!=null && name.length()!=0 && !file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				// Save file to storage if possible
				
			} catch (IOException e) {
				return null;
			}
			
		}
		
		return generatedFileId;
	}
}
