package com.bnpp.ism.business.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.bnpp.ism.api.ComponentException;
import com.bnpp.ism.api.IComponentCatalogManager;
import com.bnpp.ism.api.exchangedata.component.ComponentCatalogView;
import com.bnpp.ism.api.exchangedata.component.ComponentCategoryView;
import com.bnpp.ism.api.exchangedata.component.TechnicalComponentView;
import com.bnpp.ism.dao.component.ComponentCatalogDao;
import com.bnpp.ism.dao.component.ComponentCategoryDao;
import com.bnpp.ism.dao.component.TechnicalComponentDao;
import com.bnpp.ism.dao.storage.StoredFileVersionDao;
import com.bnpp.ism.entity.component.ComponentCatalog;
import com.bnpp.ism.entity.component.ComponentCategory;
import com.bnpp.ism.entity.component.TechnicalComponent;
import com.bnpp.ism.entity.storage.StoredFileVersion;

@Service
public class ComponentCatalogServiceManager implements IComponentCatalogManager {
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
			if (toCreate.getImage() != null
					&& toCreate.getImage().getId() != null
					&& toCreate.getImage().getId() != -1) {
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
	public List<TechnicalComponentView> getComponentsOfCategory(Long categoryId) {
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

	@Transactional
	@Override
	public List<TechnicalComponentView> searchTechnicalComponentsWithStatus(
			String status) {
		Iterable<TechnicalComponent> all = technicalComponentDao.findAll();
		if (all != null) {
			List<TechnicalComponentView> ret = new ArrayList<TechnicalComponentView>();
			for (TechnicalComponent c : all) {
				if (TechnicalComponent.WARNING_STATUS.equals(status)
						&& c.isWarningPeriod()) {
					ret.add(dozerBeanMapper
							.map(c, TechnicalComponentView.class));
				} else if (TechnicalComponent.HOT_STATUS.equals(status)
						&& c.isHotPeriod()) {
					ret.add(dozerBeanMapper
							.map(c, TechnicalComponentView.class));
				} else if (TechnicalComponent.AVAILABLE_STATUS.equals(status)
						&& c.isAvailablePeriod()) {
					ret.add(dozerBeanMapper
							.map(c, TechnicalComponentView.class));
				}
			}
			return (ret.size() > 0 ? ret : null);
		}
		return null;
	}

	@Transactional
	@Override
	public void deleteComponent(Long componentId) {
		if (componentId != null) {
			TechnicalComponent component = technicalComponentDao
					.findOne(componentId);
			if (component != null) {
				ComponentCategory parent = component.getCategory();
				parent.removeTechnicalComponent(component);
				technicalComponentDao.delete(component);
				componentCategoryDao.save(parent);

			}
		}
	}

	@Transactional
	@Override
	public ComponentCategoryView addCategory(String name, Long parentCategoryId) {
		ComponentCategory parent = componentCategoryDao
				.findOne(parentCategoryId);
		if (parent != null) {
			checkCanAddCategory(parent, name);
			ComponentCategory sub = new ComponentCategory();
			sub.setName(name);
			parent.addComponentCategory(sub);
			componentCategoryDao.save(sub);
			componentCategoryDao.save(parent);
			return dozerBeanMapper.map(sub, ComponentCategoryView.class);
		}
		ComponentException ex = new ComponentException();
		ex.setMessage("Category id=" + parentCategoryId
				+ " no longer exists, cannot add sub category [" + name + "]");
		throw ex;
	}

	private void checkCanAddCategory(ComponentCategory parent, String name) {
		if (parent.getCategories() != null) {
			for (ComponentCategory child : parent.getCategories()) {
				if (name.equals(child.getName())) {
					ComponentException ex = new ComponentException();
					ex.setMessage("Cannot create existing category : [" + name
							+ "] into [" + parent.getName() + "]");
					throw ex;
				}
			}
		}
	}

	@Transactional
	@Override
	public void deleteCategory(Long categoryId) {
		ComponentCategory cat = componentCategoryDao.findOne(categoryId);
		if (cat != null) {
			if (cat.getParent() != null) {
				cat.getParent().removeComponentCategory(cat);
				componentCategoryDao.delete(cat);
			}
		}

	}

	@Transactional
	@Override
	public List<TechnicalComponentView> searchComponentsFromFullPathName(
			String regexp) {

		HashMap<String, TechnicalComponentView> components = loadCachedComponents();
		if (components != null) {
			List<TechnicalComponentView> found = new ArrayList<TechnicalComponentView>();

			// Regexp on user interface are simplified.
			// wildcards used are just * and ?
			// * means many characters so : '.*' in java
			// ? means only one character so : '.' in java
			// So, we first transform the provided regexp
			String reg = regexp;
			reg = reg.replaceAll("\\?", ".").replaceAll("\\*", ".*");

			final Pattern pattern = Pattern.compile(reg,
					Pattern.CASE_INSENSITIVE);
			for (String key : components.keySet()) {
				Matcher matcher = pattern.matcher(key);
				if (matcher.matches()) {
					found.add(components.get(key));
				}
			}
			return (found.size() == 0 ? null : found);
		} else {
			return null;
		}
	}

	@Cacheable(value = SEARCH_COMPONENTS_FROM_FULL_PATHNAME_CACHE)
	private HashMap<String, TechnicalComponentView> loadCachedComponents() {
		Iterable<TechnicalComponent> all = technicalComponentDao.findAll();
		if (all != null) {
			HashMap<String, TechnicalComponentView> map = new HashMap<String, TechnicalComponentView>();
			for (TechnicalComponent c : all) {
				String key = c.getFullPathname();
				map.put(key,
						dozerBeanMapper.map(c, TechnicalComponentView.class));
			}
			return map;
		} else {
			return null;
		}
	}

	@Override
	@CacheEvict(value = SEARCH_COMPONENTS_FROM_FULL_PATHNAME_CACHE)
	public void evictCachedComponentsFromFullPathName() {
		// Nothing to do, eviction is done by Spring
	}
}
