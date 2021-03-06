package com.bnpp.ism.business.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bnpp.ism.api.IComponentGalleryMananager;
import com.bnpp.ism.api.IDefaultStorageSetManager;
import com.bnpp.ism.api.StorageException;
import com.bnpp.ism.api.exchangedata.component.ImageGallery;
import com.bnpp.ism.dao.component.ComponentGalleryDao;
import com.bnpp.ism.dao.storage.StoredFileVersionDao;
import com.bnpp.ism.entity.component.ComponentGallery;
import com.bnpp.ism.entity.storage.Storage;
import com.bnpp.ism.entity.storage.StorageSet;
import com.bnpp.ism.entity.storage.StoredFile;
import com.bnpp.ism.entity.storage.StoredFileVersion;

@Service
public class ComponentGalleryManager implements IComponentGalleryMananager {

	@Autowired
	IDefaultStorageSetManager storageManager;
	@Autowired
	ComponentGalleryDao galleryDao;

	@Autowired
	StoredFileVersionDao fileVersionDao;

	@Transactional
	@Override
	public void addImage(StoredFile image) {
		ComponentGallery gallery = getGallery();
		if (gallery != null) {
			gallery.addImage(image);
			galleryDao.save(gallery);
		}
	}

	
	@Override
	public void removeImageFile(StoredFileVersion image) {
		storageManager.removeFile(image.getFile());
	}

	@Transactional
	@Override
	public StoredFileVersion removeFirstInDatabase(Long imageVersionId) {
		ComponentGallery gallery = getGallery();
		if (gallery != null) {
			StoredFileVersion image = fileVersionDao.findOne(imageVersionId);
			if (gallery != null && image != null) {
				gallery.removeImage(image.getFile());
				galleryDao.save(gallery);
				storageManager.removeFileOnlyInDatabase(image.getFile());
				return image;
			}
		}
		return null;
	}

	@Transactional
	@Override
	public List<ImageGallery> getImages() {
		ComponentGallery gallery = getGallery();
		if (gallery != null) {
			List<StoredFile> images = gallery.getImages();
			if (images != null) {
				List<ImageGallery> galleryImages = new ArrayList<ImageGallery>();
				for (StoredFile image : images) {
					ImageGallery i = new ImageGallery();
					i.setFileId(image.getVersions().get(0).getId());
					i.setName(image.getName());
					i.setFileType(image.getFileType());
					galleryImages.add(i);
				}
				return galleryImages;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public boolean canStoreImages() {
		ComponentGallery gallery = getGallery();
		if (gallery != null) {
			StorageSet defaultStorage = storageManager.getDefault();
			if (defaultStorage != null) {
				for (Storage s : defaultStorage.getStorages()) {
					// If found at least one active storage, say OK...even if
					// there is no space left. This point will be addresse
					// during file upload.
					if (s.isActive())
						return true;
				}
				return false;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	private ComponentGallery getGallery() {
		Iterable<ComponentGallery> galleries = galleryDao.findAll();
		if (galleries != null) {
			return galleries.iterator().next();
		} else {
			return null;
		}
	}
}
