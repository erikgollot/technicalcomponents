package com.bnpp.ism.technicalcomponents.application.mvc;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.technicalcomponents.application.model.TechnicalComponent;
import com.bnpp.ism.technicalcomponents.application.service.TechnicalComponentService;

@RestController
public class TechnicalMainController {

	@Autowired
	private TechnicalComponentService service;

	@RequestMapping(value = "/service/technicalComponents", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<TechnicalComponent> getAll() {
		// hard coded
		List<TechnicalComponent> all = new ArrayList<TechnicalComponent>();

		TechnicalComponent c1 = new TechnicalComponent();
		c1.setId(1L);
		c1.setVendorName("Windows 8");
		all.add(c1);

		c1 = new TechnicalComponent();
		c1.setId(2L);
		c1.setVendorName("Windows 10");
		all.add(c1);

		return all;
	}
}
