package com.bnpp.ism.technicalcomponents.application.serviceimpl.component;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentCatalogDao;
import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentCategoryDao;
import com.bnpp.ism.technicalcomponents.application.dao.component.TechnicalComponentDao;
import com.bnpp.ism.technicalcomponents.application.dao.storage.StoredFileVersionDao;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCatalog;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCategory;
import com.bnpp.ism.technicalcomponents.application.model.component.TechnicalComponent;
import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFileVersion;
import com.bnpp.ism.technicalcomponents.application.model.view.component.ComponentCatalogView;
import com.bnpp.ism.technicalcomponents.application.model.view.component.TechnicalComponentView;
import com.bnpp.ism.technicalcomponents.application.service.component.ComponentCatalogService;

@Service
public class ComponentCatalogServiceImpl implements ComponentCatalogService {
	@Autowired
	private TechnicalComponentDao technicalComponentDao;

	@Autowired
	private ComponentCatalogDao catalogDao;
	@Autowired
	private ComponentCategoryDao componentCategoryDao;

	@Autowired
	private StoredFileVersionDao fileVersionDao;

	@Autowired
	Mapper dozerBeanMapper;

	@Transactional
	@Override
	public Iterable<TechnicalComponent> getAllComponents() {
		return technicalComponentDao.findAll();
	}

	@Transactional
	@Override
	public ComponentCatalog createCatalog(String name, String description) {
		ComponentCatalog catalog = new ComponentCatalog();
		catalog.setName(name);
		catalog.setDescription(description);
		catalogDao.save(catalog);
		return catalog;
	}

	@Transactional
	@Override
	public Iterable<ComponentCatalogView> catalogs() {
		Iterable<ComponentCatalog> cats = catalogDao.findAll();
		List<ComponentCatalogView> catViews = new ArrayList<ComponentCatalogView>();
		for (ComponentCatalog c : cats) {
			catViews.add(dozerBeanMapper.map(c, ComponentCatalogView.class));
		}
		return catViews;
	}

	@Transactional
	@Override
	public boolean moveCategory(Long categoryId, Long newParentId) {
		ComponentCategory newParent = componentCategoryDao.findOne(newParentId);
		ComponentCategory toMove = componentCategoryDao.findOne(categoryId);
		if (newParent != null && toMove != null) {
			ComponentCategory oldParent = toMove.getParent();
			toMove.setParent(newParent);
			oldParent.removeComponentCategory(toMove);
			newParent.addComponentCategory(toMove);

			componentCategoryDao.save(oldParent);
			componentCategoryDao.save(newParent);
			componentCategoryDao.save(toMove);
		}
		return true;
	}

	@Transactional
	@Override
	public TechnicalComponent createComponent(TechnicalComponentView toCreate,
			Long parentCategoryId) {
		ComponentCategory parent = componentCategoryDao
				.findOne(parentCategoryId);
		if (parent != null) {
			TechnicalComponent component = dozerBeanMapper.map(toCreate,
					TechnicalComponent.class);
			// To be sure it's a new one
			component.setId(null);
			component.setImage(null);

			parent.addTechnicalComponent(component);

			// If logo is not null, attache storedfileversion
			Long logoId = null;
			if (toCreate.getImage() != null && toCreate.getImage().getId()!=-1) {
				logoId = toCreate.getImage().getId();
			}
			if (logoId != null) {
				StoredFileVersion image = fileVersionDao.findOne(logoId);
				if (image != null) {
					component.setImage(image);
				}
			}

			technicalComponentDao.save(component);
			componentCategoryDao.save(parent);
			return component;
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public TechnicalComponent updateComponent(TechnicalComponentView toUpdate) {
		TechnicalComponent loaded = technicalComponentDao.findOne(toUpdate
				.getId());
		// Update does not take image into account.
		// So preserve image before copying
		StoredFileVersion image = loaded.getImage();
		// Copy from view
		dozerBeanMapper.map(toUpdate, loaded);
		// Restore image
		loaded.setImage(image);

		technicalComponentDao.save(loaded);

		return loaded;
	}

	@Transactional
	@Override
	public TechnicalComponent setImageComponent(Long componentId, Long imageId) {
		TechnicalComponent loaded = technicalComponentDao.findOne(componentId);
		if (imageId != null) {
			StoredFileVersion image = fileVersionDao.findOne(imageId);
			if (image != null) {
				loaded.setImage(image);
				technicalComponentDao.save(loaded);

			}
		}
		return loaded;
	}

	@Transactional
	@Override
	public List<TechnicalComponentView> getComponentsOfCategory(
			Long categoryId) {
		ComponentCategory cat = componentCategoryDao.findOne(categoryId);
		if (cat != null && cat.getTechnicalComponents() != null) {
			List<TechnicalComponentView> views = new ArrayList<TechnicalComponentView>();
			for (TechnicalComponent c : cat.getTechnicalComponents()) {
				views.add(dozerBeanMapper.map(c, TechnicalComponentView.class));
			}
			return views;
		} else {
			return null;
		}
	}
}
