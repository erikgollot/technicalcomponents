package com.bnpp.ism.technicalcomponents.application.configuration;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentCatalogDao;
import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentCategoryDao;
import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentGalleryDao;
import com.bnpp.ism.technicalcomponents.application.dao.component.TechnicalComponentDao;
import com.bnpp.ism.technicalcomponents.application.dao.storage.DefaultStorageSetDao;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCatalog;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCategory;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentGallery;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentVersionInfo;
import com.bnpp.ism.technicalcomponents.application.model.component.TechnicalComponent;
import com.bnpp.ism.technicalcomponents.application.model.storage.DefaultStorageSet;
import com.bnpp.ism.technicalcomponents.application.model.storage.Storage;

@Service
public class InitialContentInitializer {
	@Autowired
	ComponentCatalogDao catalog;
	@Autowired
	ComponentCategoryDao categoryDao;
	@Autowired
	TechnicalComponentDao componentDao;
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
			DefaultStorageSet storage = new DefaultStorageSet();
			Storage s = new Storage();
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

			// Add default components
			TechnicalComponent c1 = new TechnicalComponent();
			ComponentVersionInfo vendorInfo = new ComponentVersionInfo();
			vendorInfo.setName("SpringBoot");
			vendorInfo.setVersion("1.3.0");
			
			SimpleDateFormat format = new SimpleDateFormat("dd/mm/yyyy");
			try {
				vendorInfo.setAvailableDate(format.parse(
						"01/01/2015"));
				vendorInfo.setDescription("SpringBoot library from Genius");
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			c1.setVendorInformations(vendorInfo);
			
			ComponentVersionInfo localInfo = new ComponentVersionInfo();
			localInfo.setName("SpringBoot");
			localInfo.setVersion("1.3.0");
			try {
				localInfo.setAvailableDate(format.parse(
						"01/04/2015"));
				localInfo.setDescription("SpringBoot packaged by BNPP");
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			c1.setLocalInformations(localInfo);			
			open.addTechnicalComponent(c1);
			componentDao.save(c1);
			
			c1 = new TechnicalComponent();
			vendorInfo = new ComponentVersionInfo();
			vendorInfo.setName("Oracle");
			vendorInfo.setVersion("10i");
			
		
			try {
				vendorInfo.setAvailableDate(format.parse(
						"01/01/2014"));
				vendorInfo.setDescription("Oracle Database RAC");
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			c1.setVendorInformations(vendorInfo);
			
			localInfo = new ComponentVersionInfo();
			localInfo.setName("Oracle");
			localInfo.setVersion("10i");
			try {
				localInfo.setAvailableDate(format.parse(
						"27/04/2014"));
				localInfo.setDescription("Oracle Database RAC packaged by BNPP");
			} catch (ParseException e) {				
				e.printStackTrace();
			}
			c1.setLocalInformations(localInfo);			
			database.addTechnicalComponent(c1);

			componentDao.save(c1);
			catalog.save(cat);
			
		}
	}
}
