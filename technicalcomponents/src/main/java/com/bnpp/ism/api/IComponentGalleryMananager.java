package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.component.ImageGallery;
import com.bnpp.ism.entity.storage.StoredFile;
import com.bnpp.ism.entity.storage.StoredFileVersion;

public interface IComponentGalleryMananager {
	void addImage(StoredFile image);

	void removeImageFile(StoredFileVersion image);
	
	

	StoredFileVersion removeFirstInDatabase(Long imageVersionId);

	List<ImageGallery> getImages();

	boolean canStoreImages();
}
