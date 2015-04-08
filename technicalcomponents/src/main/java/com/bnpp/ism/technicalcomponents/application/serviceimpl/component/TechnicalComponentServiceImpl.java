package com.bnpp.ism.technicalcomponents.application.serviceimpl.component;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentCatalogDao;
import com.bnpp.ism.technicalcomponents.application.dao.component.ComponentCategoryDao;
import com.bnpp.ism.technicalcomponents.application.dao.component.TechnicalComponentDao;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCatalog;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCategory;
import com.bnpp.ism.technicalcomponents.application.model.component.TechnicalComponent;
import com.bnpp.ism.technicalcomponents.application.service.component.ComponentCatalogService;

@Service
public class TechnicalComponentServiceImpl implements ComponentCatalogService {
	@Autowired
	private TechnicalComponentDao technicalComponentdao;

	@Autowired
	private ComponentCatalogDao catalogDao;
	@Autowired
	private ComponentCategoryDao componentCategoryDao;
	@Autowired
	Mapper dozerBeanMapper;

	@Transactional
	@Override
	public Iterable<TechnicalComponent> getAllComponents() {
		return technicalComponentdao.findAll();
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
	public Iterable<ComponentCatalog> catalogs() {
		return catalogDao.findAll();
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
}
