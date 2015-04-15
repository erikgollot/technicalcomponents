package com.bnpp.ism.mvc.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.api.IApplicationManager;
import com.bnpp.ism.api.exchangedata.application.ApplicationVersionView;
import com.bnpp.ism.api.exchangedata.application.BuiltOnView;
import com.bnpp.ism.api.exchangedata.application.CanRunOnView;

@RestController
public class ApplicationController {
	@Autowired
	IApplicationManager appService;

	@RequestMapping(value = "/service/applications", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<ApplicationVersionView> getApplications() {
		return appService.getApplications();

	}

	@RequestMapping(value = "/service/application/setTechnicalComponents", method = RequestMethod.POST, headers = "Accept=application/json")
	public void setTechnicalComponents(
			@RequestBody ApplicationVersionView applicationVersion) {
		appService.setTechnicalComponents(applicationVersion);
	}
}
