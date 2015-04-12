package com.bnpp.ism.technicalcomponents.application.mvc.component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.bnpp.ism.technicalcomponents.application.model.component.ComponentCatalog;
import com.bnpp.ism.technicalcomponents.application.model.component.ComponentVersionInfo;
import com.bnpp.ism.technicalcomponents.application.model.component.TechnicalComponent;
import com.bnpp.ism.technicalcomponents.application.model.view.component.ComponentCatalogView;
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

	@RequestMapping(value = "/service/technicalComponents", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<TechnicalComponentView> getAll() {
		// hard coded
		List<TechnicalComponentView> all = new ArrayList<TechnicalComponentView>();

		TechnicalComponent c1 = new TechnicalComponent();
		c1.setId(1L);
		ComponentVersionInfo vendor = new ComponentVersionInfo();
		vendor.setName("Windows 8");
		vendor.setVersion("8.0.0");
		c1.setVendorInformations(vendor);
		ComponentVersionInfo local = new ComponentVersionInfo();
		local.setName("Neos");
		local.setVersion("1.0.0");

		SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");

		try {
			vendor.setAvailableDate(DATE_FORMAT.parse("01-01-2002"));
			vendor.setDeprecatedDate(DATE_FORMAT.parse("01-01-2012"));
			vendor.setUnavailableDate(DATE_FORMAT.parse("01-01-2015"));
		} catch (ParseException e) {
		}
		try {
			local.setAvailableDate(DATE_FORMAT.parse("01-01-2002"));
			local.setDeprecatedDate(DATE_FORMAT.parse("01-01-2015"));
			local.setUnavailableDate(DATE_FORMAT.parse("01-01-2017"));
		} catch (ParseException e) {
		}
		c1.setLocalInformations(local);
		all.add(dozerBeanMapper.map(c1, TechnicalComponentView.class));

		c1 = new TechnicalComponent();
		c1.setId(2L);
		vendor = new ComponentVersionInfo();
		vendor.setName("Windows 7");
		vendor.setVersion("7.6.0");
		c1.setVendorInformations(vendor);
		local = new ComponentVersionInfo();
		local.setName("Image");
		local.setVersion("2.0.0");
		c1.setLocalInformations(local);

		try {
			vendor.setAvailableDate(DATE_FORMAT.parse("01-02-2002"));
			vendor.setDeprecatedDate(DATE_FORMAT.parse("01-02-2012"));
			vendor.setUnavailableDate(DATE_FORMAT.parse("01-01-2015"));
		} catch (ParseException e) {
		}
		try {
			local.setAvailableDate(DATE_FORMAT.parse("01-02-2002"));
			local.setDeprecatedDate(DATE_FORMAT.parse("01-02-2015"));
			local.setUnavailableDate(DATE_FORMAT.parse("01-02-2017"));
		} catch (ParseException e) {
		}

		all.add(dozerBeanMapper.map(c1, TechnicalComponentView.class));

		return all;
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
	void updateComponent(
			@RequestParam("component") TechnicalComponentView toUpdate) {

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
}
