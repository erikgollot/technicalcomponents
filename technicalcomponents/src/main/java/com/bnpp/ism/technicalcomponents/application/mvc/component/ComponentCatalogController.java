package com.bnpp.ism.technicalcomponents.application.mvc.component;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCatalog;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCategory;
import com.bnpp.ism.technicalcomponents.application.model.component.TechnicalComponent;
import com.bnpp.ism.technicalcomponents.application.model.view.component.ComponentCatalogView;
import com.bnpp.ism.technicalcomponents.application.model.view.component.ComponentCategoryView;
import com.bnpp.ism.technicalcomponents.application.model.view.component.TechnicalComponentView;
import com.bnpp.ism.technicalcomponents.application.service.component.ComponentCatalogService;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
public class ComponentCatalogController {

	@Autowired
	private ComponentCatalogService service;
	// TODO remove after tests
	@Autowired
	Mapper dozerBeanMapper;

	@RequestMapping(value = "/service/searchTechnicalComponentsWithStatus/{status}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<TechnicalComponentView> searchTechnicalComponentsWithStatus(
			@PathVariable("status") String status) {
		return service.searchTechnicalComponentsWithStatus(status);
	}

	@RequestMapping(value = "/service/create", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ComponentCatalogView createCatalog(@RequestBody ComponentCatalogView cat) {
		ComponentCatalog catalog = service.createCatalog(cat.getName(),
				cat.getDescription());
		if (catalog != null) {
			return dozerBeanMapper.map(catalog, ComponentCatalogView.class);
		} else {
			return null;
		}
	}

	@RequestMapping(value = "/service/catalogs", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<ComponentCatalogView> catalogs() {
		Iterable<ComponentCatalogView> all = service.catalogs();

		List<ComponentCatalogView> ret = new ArrayList<ComponentCatalogView>();
		for (ComponentCatalogView c : all) {
			ret.add(c);
		}
		return ret;

	}

	@RequestMapping(value = "/service/moveCategory/{idNewParent}/{idMovedCategory}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	boolean moveCategory(@PathVariable("idNewParent") Long idNewParent,
			@PathVariable("idMovedCategory") Long idMovedCategory) {
		return service.moveCategory(idMovedCategory, idNewParent);
	}

	@RequestMapping(value = "/service/createComponent/{idParentCategory}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	TechnicalComponentView createComponent(
			@RequestBody TechnicalComponentView toCreate,
			@PathVariable("idParentCategory") Long parentCategoryId) {

		TechnicalComponent created = service.createComponent(toCreate,
				parentCategoryId);
		if (created != null) {
			return dozerBeanMapper.map(created, TechnicalComponentView.class);
		} else {
			return null;
		}

	}

	@RequestMapping(value = "/service/updateComponent", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	@ApiOperation(value = "updateComponent", notes = "Image update is not taken into account here")
	void updateComponent(@RequestBody TechnicalComponentView toUpdate) {

		TechnicalComponent updated = service.updateComponent(toUpdate);

	}

	@RequestMapping(value = "/service/setImageComponent/{idComponent}/{idImage}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	@ApiOperation(value = "setImageComponent", notes = "Image update is not taken into account here")
	void setImageComponent(@PathVariable("idComponent") Long idComponent,
			@PathVariable("idImage") Long idImage) {
		service.setImageComponent(idComponent, idImage);
	}

	@RequestMapping(value = "/service/componentsOfCategory/{idCategory}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<TechnicalComponentView> getComponentsOfCategory(
			@PathVariable("idCategory") Long categoryId) {
		return service.getComponentsOfCategory(categoryId);
	}

	@RequestMapping(value = "/service/deleteComponent/{idComponent}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteComponent(@PathVariable("idComponent") Long idComponent) {
		service.deleteComponent(idComponent);
	}

	@RequestMapping(value = "/service/addCategory/{idParent}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ComponentCategoryView addCategory(@RequestBody String newCategoryName,
			@PathVariable("idParent") Long idParent) {
		return service.addCategory(newCategoryName, idParent);		
	}
	
	@RequestMapping(value = "/service/deleteCategory/{idCategory}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteCategory(@PathVariable("idCategory") Long idCategory) {
		service.deleteCategory(idCategory);
	}
}
