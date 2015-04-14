package com.bnpp.ism.technicalcomponents.application.mvc.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.technicalcomponents.application.model.view.application.ApplicationVersionView;
import com.bnpp.ism.technicalcomponents.application.service.application.ApplicationService;

@RestController
public class ApplicationController {
	@Autowired
	ApplicationService appService;

	@RequestMapping(value = "/service/applications", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<ApplicationVersionView> getApplications() {
		return appService.getApplications();

	}

}
