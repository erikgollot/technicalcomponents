package com.bnpp.ism.technicalcomponents.application.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentCatalogDao;
import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentCategoryDao;
import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentGalleryDao;
import com.bnpp.ism.technicalcomponents.application.dao.storage.DefaultStorageSetDao;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCatalog;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCategory;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentGallery;
import com.bnpp.ism.technicalcomponents.application.model.storage.DefaultStorageSet;
import com.bnpp.ism.technicalcomponents.application.model.storage.Storage;

@Service
public class InitialContentInitializer {
	@Autowired
	ComponentCatalogDao catalog;
	@Autowired
	ComponentCategoryDao categoryDao;

	@Autowired
	ComponentGalleryDao galleryDao;
	@Autowired
	DefaultStorageSetDao storageDao;

	@PostConstruct
	@Transactional
	void initDB() {
		// Default component catalog
		initDefaultCatalog();

		// Default Gallery Catalog
		initGalleryCatalog();

		initDefaultStorage();
	}

	private void initDefaultStorage() {
		Iterable<DefaultStorageSet> storages = storageDao.findAll();
		if (!storages.iterator().hasNext()) {		
			DefaultStorageSet  storage = new DefaultStorageSet();
			Storage s =new Storage();
			s.setActive(true);
			s.setOrderInSet(1);
			s.setQuota(100);
			s.setRootDir("c:/ismstorage/store1");
			storage.addStorage(s);
			s.setStorageSet(storage);
			storageDao.save(storage);
		}
	}

	private void initGalleryCatalog() {
		Iterable<ComponentGallery> galleries = galleryDao.findAll();
		if (galleries == null || galleries.iterator() == null
				|| !galleries.iterator().hasNext()) {
			ComponentGallery gallery = new ComponentGallery();
			galleryDao.save(gallery);
		}
	}

	private void initDefaultCatalog() {
		if (catalog.getDefault() == null) {
			ComponentCatalog cat = new ComponentCatalog();
			cat.setName(ComponentCatalog.DEFAULT);
			cat.setDescription("Default Global Catalog");

			ComponentCategory mainframe = new ComponentCategory();
			mainframe.setName("Mainframe");
			mainframe.setDescription("Mainfraime components");
			mainframe.setParent(null);
			cat.addComponentCategory(mainframe);
			ComponentCategory cobolCompilers = new ComponentCategory();
			cobolCompilers.setName("Cobol compilers");
			cobolCompilers.setDescription("Cobol compilers");
			cobolCompilers.setParent(mainframe);
			mainframe.addComponentCategory(cobolCompilers);

			ComponentCategory open = new ComponentCategory();
			open.setName("Open");
			open.setDescription("Open components");
			cat.addComponentCategory(open);

			ComponentCategory database = new ComponentCategory();
			database.setName("database");
			database.setDescription("Open components");
			database.setParent(open);
			open.addComponentCategory(database);

			catalog.save(cat);
		}
	}
}
