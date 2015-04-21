package com.bnpp.ism.mvc.application;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.api.IApplicationManager;
import com.bnpp.ism.api.IApplicationVersionKpiSnapshotManager;
import com.bnpp.ism.api.exchangedata.application.ApplicationVersionView;
import com.bnpp.ism.api.exchangedata.kpi.value.ApplicationVersionKpiSnapshotView;

@RestController
public class ApplicationController {
	@Autowired
	IApplicationManager appService;
	@Autowired
	IApplicationVersionKpiSnapshotManager snapshotService;

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

	@RequestMapping(value = "/service/createSnapshot/{applicationVersionId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ApplicationVersionKpiSnapshotView createSnapshot(
			@PathVariable("applicationVersionId") Long applicationVersionId) {
		return snapshotService.createSnapshot(applicationVersionId);

	}

	@RequestMapping(value = "/service/getSnapshots/{applicationVersionId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<ApplicationVersionKpiSnapshotView> getSnapshots(
			@PathVariable("applicationVersionId") Long applicationVersionId) {
		return snapshotService.getSnapshots(applicationVersionId);

	}
}
