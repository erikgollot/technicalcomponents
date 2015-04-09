package com.bnpp.ism.technicalcomponents.application.serviceimpl.component;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentGalleryDao;
import com.bnpp.ism.technicalcomponents.application.dao.storage.StoredFileVersionDao;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentGallery;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFile;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFileVersion;
import com.bnpp.ism.technicalcomponents.application.model.view.component.ImageGallery;
import com.bnpp.ism.technicalcomponents.application.service.component.ComponentGalleryManager;
import com.bnpp.ism.technicalcomponents.application.service.storage.DefaultStorageSetManager;

@Service
public class ComponentGalleryManagerImpl implements ComponentGalleryManager {

	@Autowired
	DefaultStorageSetManager storageManager;
	@Autowired
	ComponentGalleryDao galleryDao;

	@Autowired
	StoredFileVersionDao fileVersionDao;

	@Transactional
	@Override
	public void addImage(StoredFile image) {
		Iterable<ComponentGallery> galleries = galleryDao.findAll();
		if (galleries != null) {
			ComponentGallery gallery = galleries.iterator().next();
			gallery.addImage(image);
			galleryDao.save(gallery);
		}
	}

	@Transactional
	@Override
	public void removeImage(Long imageVersionId) {
		Iterable<ComponentGallery> galleries = galleryDao.findAll();
		if (galleries != null) {
			ComponentGallery gallery = galleries.iterator().next();
			StoredFileVersion image = fileVersionDao.findOne(imageVersionId);
			if (gallery != null && image != null) {
				gallery.removeImage(image.getFile());
				galleryDao.save(gallery);
				// Delete image
				storageManager.removeFile(image.getFile());

			}
		}
	}

	@Transactional
	@Override
	public List<ImageGallery> getImages() {
		Iterable<ComponentGallery> galleries = galleryDao.findAll();
		if (galleries != null) {
			ComponentGallery gallery = galleries.iterator().next();
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
}
