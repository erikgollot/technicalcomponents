package com.bnpp.ism.mvc.component;

import java.io.IOException;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bnpp.ism.api.IComponentGalleryMananager;
import com.bnpp.ism.api.IDefaultStorageSetManager;
import com.bnpp.ism.api.StorageException;
import com.bnpp.ism.api.exchangedata.component.ImageGallery;
import com.bnpp.ism.entity.storage.StoredFile;
import com.bnpp.ism.entity.storage.StoredFileVersion;

@RestController
public class ComponentGalleryController {
	@Autowired
	IDefaultStorageSetManager storageManager;
	@Autowired
	IComponentGalleryMananager galleryManager;
	
	@Autowired
	Mapper dozerBeanMapper;

	@RequestMapping(value = "/componentGallery/storeImage", method = RequestMethod.POST)
	public @ResponseBody
	ImageGallery uploadFile(@RequestParam("file") MultipartFile file) {

		if (file != null && file.getOriginalFilename() != null
				&& file.getOriginalFilename().length() != 0 && !file.isEmpty()) {
			try {
				byte[] bytes = file.getBytes();
				StoredFile fd = storageManager.store(
						file.getOriginalFilename(), bytes);
				galleryManager.addImage(fd);
				StoredFileVersion version1 = fd.getVersions().get(0);
				ImageGallery img = new ImageGallery();
				img.setFileId(version1.getId());
				img.setName(fd.getName());
				img.setFileType(fd.getFileType());
				return img;

			} catch (IOException e) {
				StorageException ex = new StorageException();
				ex.setMessage("Cannot read file content from request");
				throw ex;

			}

		} else {
			return null;
		}
	}

	@RequestMapping(value = "/componentGallery/removeImage/{id}", method = RequestMethod.POST)
	public @ResponseBody
	void removeImage(@PathVariable("id") Long idOfVersion) {
		if (idOfVersion != null) {
			try {
				StoredFileVersion version = galleryManager.removeFirstInDatabase(idOfVersion);
				storageManager.removePhysicalVersions(version.getFile());				
			} catch (RuntimeException ex) {
				StorageException ex2 = new StorageException();
				ex2.setMessage("Cannot remove image, image is used by a component");
				throw ex2;
			}
		}
	}

	@RequestMapping(value = "/componentGallery/all", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<ImageGallery> all() {
		return galleryManager.getImages();
	}

	@RequestMapping(value = "/componentGallery/canStoreImage", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	boolean canStoreImages() {
		return galleryManager.canStoreImages();
	}

}
