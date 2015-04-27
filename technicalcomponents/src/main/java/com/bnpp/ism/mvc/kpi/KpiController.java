package com.bnpp.ism.mvc.kpi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.api.IKpiManager;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiListView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiEnumLiteralView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView;

@RestController
public class KpiController {

	@Autowired
	IKpiManager manager;

	@RequestMapping(value = "/service/kpi/kpis", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	AbstractKpiListView loadAllKpiDefinitions() {
		return manager.loadAllKpiDefinitions();
	}

	@RequestMapping(value = "/service/kpi/createEnumKpi", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ManualEnumKpiView createEnumKpi(@RequestBody ManualEnumKpiView kpi) {
		return manager.createEnumKpi(kpi);
	}

	@RequestMapping(value = "/service/kpi/updateEnumKpi", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void updateEnumKpi(@RequestBody ManualEnumKpiView kpi) {
		manager.updateEnumKpi(kpi);
	}

	@RequestMapping(value = "/service/kpi/deleteLiteral", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteLiteral(@RequestBody Long literalId) {
		manager.deleteLiteral(literalId);
	}

	@RequestMapping(value = "/service/kpi/addLiteral/{kpiId}", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	KpiEnumLiteralView addLiteral(@PathVariable("kpiId") Long kpiId,
			@RequestBody KpiEnumLiteralView literal) {
		return manager.addLiteral(kpiId, literal);
	}

	@RequestMapping(value = "/service/kpi/updateLiteral", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void updateLiteral(@RequestBody KpiEnumLiteralView literal) {
		manager.updateLiteral(literal);
	}

	@RequestMapping(value = "/service/kpi/createNumericKpi", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	ManualNumericKpiView createNumericKpi(@RequestBody ManualNumericKpiView kpi) {
		return manager.createNumericKpi(kpi);
	}

	@RequestMapping(value = "/service/kpi/updateNumericKpi", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void updateNumericKpi(@RequestBody ManualNumericKpiView kpi) {
		manager.updateNumericKpi(kpi);
	}

	@RequestMapping(value = "/service/kpi/deleteKpi", method = RequestMethod.POST, headers = "Accept=application/json")
	public @ResponseBody
	void deleteKpi(@RequestBody Long kpiId) {
		manager.deleteKpi(kpiId);
	}

}
