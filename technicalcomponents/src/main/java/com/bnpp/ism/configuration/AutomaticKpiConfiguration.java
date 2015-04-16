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
	}

	private void applicationDocumentationKpi() {
		ManualEnumKpi documentation_quality = new ManualEnumKpi();
		documentation_quality.setName("Application documentation quality");
		documentation_quality
				.setDescription("Used to assess the conformity of the application's documentation to standards.<br>Values are :<br><ul> <li>BAD (1)</li><li>NEED IMPROVEMENTS (3)</li><li>CORRECT (5)</li></ul>");
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
		applicationObsolescenceKpiDao.save(o);
	}
}
