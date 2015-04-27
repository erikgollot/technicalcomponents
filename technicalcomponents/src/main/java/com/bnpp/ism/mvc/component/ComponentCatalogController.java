package com.bnpp.ism.mvc.component;

import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.api.IComponentCatalogManager;
import com.bnpp.ism.api.exchangedata.component.ComponentCatalogView;
import com.bnpp.ism.api.exchangedata.component.ComponentCategoryView;
import com.bnpp.ism.api.exchangedata.component.TechnicalComponentView;
import com.bnpp.ism.entity.component.ComponentCatalog;
import com.bnpp.ism.entity.component.TechnicalComponent;
import com.wordnik.swagger.annotations.ApiOperation;

@RestController
public class ComponentCatalogController {

	@Autowired
	private IComponentCatalogManager service;
	// TODO remove after tests
	@Autowired
	Mapper dozerBeanMapper;

	@RequestMapping(value = "/service/component/searchTechnicalComponentsWithStatus/{status}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<TechnicalComponentView> searchTechnicalComponentsWithStatus(
			@PathVariable("status") String status) {
		return service.searchTechnicalComponentsWithStatus(status);
	}

	@RequestMapping(value = "/service/component/createCatalog", method = RequestMethod.POST, headers = "Accept=application/json")
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

	@RequestMapping(value = "/service/component/catalogs", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<ComponentCatalogView> catalogs() {
		Iterable<ComponentCatalogView> all = service.catalogs();

		List<ComponentCatalogView> ret = new ArrayList<ComponentCatalogView>();
		for (ComponentCatalogView c : all) {
			ret.add(c);
		}
		return ret;

	}

	@RequestMapping(value = "/service/component/moveCategory/{idNewParent}/{idMovedCategory}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	boolean moveCategory(@PathVariable("idNewParent") Long idNewParent,
			@PathVariable("idMovedCategory") Long idMovedCategory) {
		return service.moveCategory(idMovedCategory, idNewParent);
	}

	@RequestMapping(value = "/service/component/createComponent/{idParentCategory}", method = RequestMethod.POST, headers = "Accept=application/json")
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

	@RequestMapping(value = "/service/component/updateComponent", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	@ApiOperation(value = "updateComponent", notes = "Image update is not taken into account here")
	void updateComponent(@RequestBody TechnicalComponentView toUpdate) {

		TechnicalComponent updated = service.updateComponent(toUpdate);

	}

	@RequestMapping(value = "/service/component/setImageComponent/{idComponent}/{idImage}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	@ApiOperation(value = "setImageComponent", notes = "Image update is not taken into account here")
	void setImageComponent(@PathVariable("idComponent") Long idComponent,
			@PathVariable("idImage") Long idImage) {
		service.setImageComponent(idComponent, idImage);
	}

	@RequestMapping(value = "/service/component/componentsOfCategory/{idCategory}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<TechnicalComponentView> getComponentsOfCategory(
			@PathVariable("idCategory") Long categoryId) {
		return service.getComponentsOfCategory(categoryId);
	}

	@RequestMapping(value = "/service/component/deleteComponent/{idComponent}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteComponent(@PathVariable("idComponent") Long idComponent) {
		service.deleteComponent(idComponent);
	}

	@RequestMapping(value = "/service/component/addCategory/{idParent}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ComponentCategoryView addCategory(@RequestBody String newCategoryName,
			@PathVariable("idParent") Long idParent) {
		return service.addCategory(newCategoryName, idParent);		
	}
	
	@RequestMapping(value = "/service/component/deleteCategory/{idCategory}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteCategory(@PathVariable("idCategory") Long idCategory) {
		service.deleteCategory(idCategory);
	}
	
	@RequestMapping(value = "/service/component/searchComponentsFromFullPathName", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<TechnicalComponentView> searchComponentsFromFullPathName(
			@RequestParam("regexp") String regexp) {
		return service.searchComponentsFromFullPathName(regexp);
	}
	
	
	@RequestMapping(value = "/service/component/changeCategoryName/{categoryId}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void changeCategoryName(@PathVariable("categoryId") Long categoryId, @RequestParam("newName") String newName) {
		service.changeCategoryName(categoryId, newName);
	}
}
