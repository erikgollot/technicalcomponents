package com.bnpp.ism.technicalcomponents.application.service.component;

import java.util.List;

import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFile;
import com.bnpp.ism.technicalcomponents.application.model.view.component.ImageGallery;

public interface ComponentGalleryManager {
	void addImage(StoredFile image);

	void removeImage(Long imageId);

	List<ImageGallery> getImages();
}
