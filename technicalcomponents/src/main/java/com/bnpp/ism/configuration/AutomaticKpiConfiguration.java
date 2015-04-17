package com.bnpp.ism.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.bnpp.ism.dao.kpi.application.AbstractKpiDao;
import com.bnpp.ism.dao.kpi.application.ApplicationObsolescenceKpiDao;
import com.bnpp.ism.entity.kpi.application.ApplicationObsolescenceKpi;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiEnumLiteral;
import com.bnpp.ism.entity.kpi.metadata.ManualEnumKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualNumericKpi;

@Configuration
public class AutomaticKpiConfiguration {
	@Autowired
	ApplicationObsolescenceKpiDao applicationObsolescenceKpiDao;
	@Autowired
	AbstractKpiDao abstractKpiDao;

	@PostConstruct
	@Transactional
	void initDB() {
		Iterable<AbstractKpi> alls = abstractKpiDao.findAll();
		if (!alls.iterator().hasNext()) {
			initManualDefaults();
			initComputed();
		}
	}

	private void initManualDefaults() {
		applicationDocumentationKpi();
		endUserSatisfactionnKpi();
	}

	private void endUserSatisfactionnKpi() {
		ManualNumericKpi endusersatisfaction = new ManualNumericKpi();
		endusersatisfaction.setName("Application end-user satisfaction");
		endusersatisfaction.setDescription("Used to asses the end-user satisfaction index.<br>Value is an integer from 0 : <b>very bad</b> to 5 : <b>excellent</b>");
		endusersatisfaction.setInt(true);
		endusersatisfaction.setMinValue(0.0f);
		endusersatisfaction.setMaxValue(5.0f);
		
		abstractKpiDao.save(endusersatisfaction);
	}

	private void applicationDocumentationKpi() {
		ManualEnumKpi documentation_quality = new ManualEnumKpi();
		documentation_quality.setName("Application documentation quality");
		documentation_quality
				.setDescription("Used to assess the conformity of the application's documentation to standards.<br>Values are :<br><ul> <li><b>BAD</b> (1)</li><li><b>NEED IMPROVEMENTS</b> (3)</li><li><b>CORRECT</b> (5)</li></ul>");
		KpiEnumLiteral bad_documentation = new KpiEnumLiteral();
		bad_documentation.setName("BAD");
		bad_documentation.setRank(1);
		bad_documentation.setValue(1.0f);
		documentation_quality.addLiteral(bad_documentation);
		KpiEnumLiteral need_improvement_documentation = new KpiEnumLiteral();
		need_improvement_documentation.setName("NEED IMPROVEMENTS");
		need_improvement_documentation.setRank(2);
		need_improvement_documentation.setValue(3.0f);
		documentation_quality.addLiteral(need_improvement_documentation);
		KpiEnumLiteral correct_improvement_documentation = new KpiEnumLiteral();
		correct_improvement_documentation.setName("CORRECT");
		correct_improvement_documentation.setRank(3);
		correct_improvement_documentation.setValue(5.0f);
		documentation_quality.addLiteral(correct_improvement_documentation);
		abstractKpiDao.save(documentation_quality);
	}

	private void initComputed() {
		ApplicationObsolescenceKpi o = new ApplicationObsolescenceKpi();
		o.setDescription("Application obsolescence computed from status of all associated technical components.<br>Values are:<br><ul><li><b>AVAILABLE</b> : all components are in their AVAILABILTY period</li><li><b>WARNING</b> : at least one component is in its WARNING period (it' time to upgrade to a newer version)</li><li><b>HOT</b> : at least one component is in its HOT period (you do not have too must time to think to upgrade to a newer version)</li></ul>");
		applicationObsolescenceKpiDao.save(o);
	}
}
