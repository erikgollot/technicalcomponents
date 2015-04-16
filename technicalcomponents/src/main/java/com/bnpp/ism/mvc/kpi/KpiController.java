package com.bnpp.ism.mvc.kpi;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.api.IKpiManager;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;

@RestController
public class KpiController {

	@Autowired
	IKpiManager manager;

	@RequestMapping(value = "/service/kpis", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<AbstractKpiView> loadAllKpiDefinitions() {
		return manager.loadAllKpiDefinitions();
	}

}
