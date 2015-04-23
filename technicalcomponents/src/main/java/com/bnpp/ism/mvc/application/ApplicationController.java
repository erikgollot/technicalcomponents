package com.bnpp.ism.mvc.application;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.api.IApplicationManager;
import com.bnpp.ism.api.IApplicationVersionKpiSnapshotManager;
import com.bnpp.ism.api.exchangedata.application.ApplicationKpiDashboard;
import com.bnpp.ism.api.exchangedata.application.ApplicationVersionView;
import com.bnpp.ism.api.exchangedata.application.ApplicationView;
import com.bnpp.ism.api.exchangedata.kpi.value.ApplicationVersionKpiSnapshotView;
import com.bnpp.ism.api.exchangedata.kpi.value.ManualUserMeasurementView;

@RestController
public class ApplicationController {
	@Autowired
	IApplicationManager appService;
	@Autowired
	IApplicationVersionKpiSnapshotManager snapshotService;

	@RequestMapping(value = "/service/applications", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<ApplicationView> getApplications() {
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

	@RequestMapping(value = "/service/updateSnapshot", method = RequestMethod.POST, headers = "Accept=application/json")
	public ApplicationVersionKpiSnapshotView updateSnapshot(
			@RequestParam("snapshotId") Long snapshotId,
			@RequestParam("frozen") boolean frozen,
			@RequestParam("forDate") String forDate) {
		// @DateTimeFormat(iso=ISO.DATE)

		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
			Date date = format.parse(forDate);
			return snapshotService.updateSnapshot(snapshotId, frozen, date);
		} catch (ParseException e) {
			throw new RuntimeException(e.getMessage());
		}

	}

	@RequestMapping(value = "/service/getSnapshots/{applicationVersionId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<ApplicationVersionKpiSnapshotView> getSnapshots(
			@PathVariable("applicationVersionId") Long applicationVersionId) {
		return snapshotService.getSnapshots(applicationVersionId);

	}

	@RequestMapping(value = "/service/createMeasurement", method = RequestMethod.POST, headers = "Accept=application/json")
	public ManualUserMeasurementView createMeasurement(
			@RequestParam("snapshotId") Long snapshotId,
			@RequestParam("userId") Long userId) {
		return snapshotService.createMeasurement(snapshotId, userId);
	}

	@RequestMapping(value = "/service/updateMeasurement", method = RequestMethod.POST, headers = "Accept=application/json")
	public ManualUserMeasurementView updateMeasurement(
			@RequestBody ManualUserMeasurementView measurement) {
		return snapshotService.updateMeasurement(measurement);
	}

	@RequestMapping(value = "/service/deleteSnapshot/{snapshotId}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteSnapshot(@PathVariable("snapshotId") Long snapshotId) {
		snapshotService.deleteSnapshot(snapshotId);

	}

	@RequestMapping(value = "/service/deleteMeasurement/{measurementId}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteMeasurement(@PathVariable("measurementId") Long measurementId) {
		snapshotService.deleteMeasurement(measurementId);

	}

	@RequestMapping(value = "/service/getApplicationDashboard/{applicationId}", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	ApplicationKpiDashboard getApplicationDashboard(
			@PathVariable("applicationId") Long applicationId) {
		return snapshotService.getApplicationDashboard(applicationId);

	}
}
