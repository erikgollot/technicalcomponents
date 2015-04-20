package com.bnpp.ism.mvc.kpi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.api.IKpiManager;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiConfigurationView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiEnumLiteralView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView;

@RestController
public class KpiController {

	@Autowired
	IKpiManager manager;

	@RequestMapping(value = "/service/kpis", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<AbstractKpiView> loadAllKpiDefinitions() {
		return manager.loadAllKpiDefinitions();
	}

	@RequestMapping(value = "/service/kpiconfigurations", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<KpiConfigurationView> loadAllKpiConfigurations() {
		return manager.getAllConfigurations();
	}

	@RequestMapping(value = "/service/newKpiConfiguration", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	KpiConfigurationView createNewKpiConfiguration(
			@RequestParam("name") String name,
			@RequestParam("description") String description) {
		return manager.createNewConfiguration(name, description);
	}

	@RequestMapping(value = "/service/updateKpiConfiguration", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	KpiConfigurationView updateConfiguration(
			@RequestBody KpiConfigurationView config) {
		return manager.updateConfiguration(config);
	}

	@RequestMapping(value = "/service/createEnumKpi", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ManualEnumKpiView createEnumKpi(@RequestBody ManualEnumKpiView kpi) {
		return manager.createEnumKpi(kpi);
	}

	@RequestMapping(value = "/service/updateEnumKpi", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void updateEnumKpi(@RequestBody ManualEnumKpiView kpi) {
		manager.updateEnumKpi(kpi);
	}

	@RequestMapping(value = "/service/deleteLiteral", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteLiteral(@RequestBody Long literalId) {
		manager.deleteLiteral(literalId);
	}
	
	@RequestMapping(value = "/service/addLiteral/{kpiId}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	KpiEnumLiteralView addLiteral(@PathVariable("kpiId") Long kpiId, @RequestBody KpiEnumLiteralView literal) {
		return manager.addLiteral(kpiId,literal);
	}
	
	@RequestMapping(value = "/service/updateLiteral", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void updateLiteral(@RequestBody KpiEnumLiteralView literal) {
		manager.updateLiteral(literal);
	}

	@RequestMapping(value = "/service/createNumericKpi", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ManualNumericKpiView createNumericKpi(@RequestBody ManualNumericKpiView kpi) {
		return manager.createNumericKpi(kpi);
	}

	@RequestMapping(value = "/service/updateNumericKpi", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void updateNumericKpi(@RequestBody ManualNumericKpiView kpi) {
		manager.updateNumericKpi(kpi);
	}

	@RequestMapping(value = "/service/deleteKpi", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteKpi(@RequestBody Long kpiId) {
		manager.deleteKpi(kpiId);
	}

}
