package com.bnpp.ism.technicalcomponents.application.mvc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.technicalcomponents.application.model.ComponentVersionInfo;
import com.bnpp.ism.technicalcomponents.application.model.TechnicalComponent;
import com.bnpp.ism.technicalcomponents.application.model.view.TechnicalComponentView;
import com.bnpp.ism.technicalcomponents.application.service.TechnicalComponentService;

@RestController
public class TechnicalMainController {

	@Autowired
	private TechnicalComponentService service;
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
}
