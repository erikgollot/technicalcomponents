package com.bnpp.ism.configuration;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.Transactional;

import com.bnpp.ism.dao.kpi.application.ApplicationObsolescenceKpiDao;
import com.bnpp.ism.dao.kpi.metadata.AbstractKpiDao;
import com.bnpp.ism.entity.kpi.application.ApplicationObsolescenceKpi;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiEnumLiteral;
import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;
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

	/**
	 * KA2a - End-user satisfaction index KC1 - Run cost per total landscape
	 * function points KC2a - Run phase incident density per month KC2b -
	 * Incident stock resolution variability KC2c - Application cost complexity
	 * KC2d - Application landscape obsolescence - Rouge (hot) : si score
	 * inférieur à 2. On passe à 2 si fin de support ou fin commercialisation
	 * +5ans - Orange (warning) : si score >= 2 et un des éléments au moins < 2
	 * - Vert (available) : si tout >=2 KC3 - Incident SLA compliance
	 */
	private void initManualDefaults() {
		applicationDocumentationKpi();

		// PMS BNPP
		KA2a_End_user_satisfaction_index();
		KC1_Run_Cost_Per_Iotal_Landscape_Function_Points();
		KC2a_Run_phase_incident_density_per_month();
		KC2b_Incident_stock_resolution_variability();
		KC2c_Application_cost_complexity();
		KC2d_Application_landscape_obsolescence();
		KC3_Incident_SLA_compliance();
	}

	private static final String quality_risk = "Quality / Risk";
	private static final String cost_efficiency = "Cost efficiency";
	private static final String responsiveness = "Responsiveness";

	private void KC3_Incident_SLA_compliance() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Incident SLA compliance");
		kpi.setDescription("Performance Measurement of the Application Maintenance service for incident resolution against the target SLAs.<br>Calculated monthly on a 12 months rolling period");
		kpi.setInt(false);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(100.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(responsiveness);

		abstractKpiDao.save(kpi);
	}

	/**
	 * TODO review definition with input of Philippe Lievre
	 */
	private void KC2d_Application_landscape_obsolescence() {
		ManualEnumKpi kpi = new ManualEnumKpi();
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_ENUM);
		kpi.setName("Application landscape obsolescence");
		kpi.setDescription("A measure of the non-alignment of the application portfolio with the IT strategy due to obsolete underlying technologies.");
		kpi.setCategory(quality_risk);

		KpiEnumLiteral hot = new KpiEnumLiteral();
		hot.setName("HOT");
		hot.setRank(1);
		hot.setValue(1.0f);
		kpi.addLiteral(hot);
		KpiEnumLiteral warning = new KpiEnumLiteral();
		warning.setName("WARNING");
		warning.setRank(2);
		warning.setValue(3.0f);
		kpi.addLiteral(warning);
		KpiEnumLiteral available = new KpiEnumLiteral();
		available.setName("AVAILABLE");
		available.setRank(3);
		available.setValue(5.0f);
		kpi.addLiteral(available);
		abstractKpiDao.save(kpi);
	}

	private void KC2c_Application_cost_complexity() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Application cost complexity");
		kpi.setDescription("Estimation of remediation costs linked to code non quality and associated potential business impacts.<br>Calculated on a 12 months period.");
		kpi.setInt(false);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(Float.MAX_VALUE);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(quality_risk);
		abstractKpiDao.save(kpi);
	}

	private void KC2b_Incident_stock_resolution_variability() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Incident stock resolution variability");
		kpi.setDescription("The variability of all incidents relating to critical applications which need to be fulfilled by the application development and maintenance (ADM) team.<br>Calculated monthly on a 12 months rolling period.");
		kpi.setInt(false);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(Float.MAX_VALUE);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(quality_risk);
		abstractKpiDao.save(kpi);
	}

	private void KC2a_Run_phase_incident_density_per_month() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Run phase incident density per month");
		kpi.setDescription("This is a measurement of the relative incident density of the application portfolio used to ensure that the robustness and stability of the service improves over time.<br>Calculated monthly on a 12 months rolling period.");
		kpi.setInt(false);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(Float.MAX_VALUE);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(quality_risk);
		abstractKpiDao.save(kpi);

	}

	private void KC1_Run_Cost_Per_Iotal_Landscape_Function_Points() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Run cost per total landscape function points");
		kpi.setDescription("To measure the total cost spent on the run against the size maintained (run cost per FP) - excluding software packages maintenance fees");
		kpi.setInt(false);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(Float.MAX_VALUE);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(cost_efficiency);
		abstractKpiDao.save(kpi);
	}

	private void KA2a_End_user_satisfaction_index() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("End-user satisfaction index");
		kpi.setDescription("Used to asses the end-user satisfaction index.<br>Value is an integer from 0 : <b>very bad</b> to 5 : <b>excellent</b>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(5.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(quality_risk);
		abstractKpiDao.save(kpi);
	}

	private void applicationDocumentationKpi() {
		ManualEnumKpi kpi = new ManualEnumKpi();
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_ENUM);
		kpi.setName("Application documentation quality");
		kpi.setDescription("Used to assess the conformity of the application's documentation to standards.<br>Values are :<br><ul> <li><b>BAD</b> (1)</li><li><b>NEED IMPROVEMENTS</b> (3)</li><li><b>CORRECT</b> (5)</li></ul>");
		KpiEnumLiteral bad_documentation = new KpiEnumLiteral();
		bad_documentation.setName("BAD");
		bad_documentation.setRank(1);
		bad_documentation.setValue(1.0f);
		kpi.addLiteral(bad_documentation);
		KpiEnumLiteral need_improvement_documentation = new KpiEnumLiteral();
		need_improvement_documentation.setName("NEED IMPROVEMENTS");
		need_improvement_documentation.setRank(2);
		need_improvement_documentation.setValue(3.0f);
		kpi.addLiteral(need_improvement_documentation);
		KpiEnumLiteral correct_improvement_documentation = new KpiEnumLiteral();
		correct_improvement_documentation.setName("CORRECT");
		correct_improvement_documentation.setRank(3);
		correct_improvement_documentation.setValue(5.0f);
		kpi.addLiteral(correct_improvement_documentation);
		abstractKpiDao.save(kpi);
	}

	private void initComputed() {
		ApplicationObsolescenceKpi o = new ApplicationObsolescenceKpi();
		o.setDescription("Application obsolescence computed from status of all associated technical components.<br>Values are:<br><ul><li><b>AVAILABLE</b> : all components are in their AVAILABILTY period</li><li><b>WARNING</b> : at least one component is in its WARNING period (it' time to upgrade to a newer version)</li><li><b>HOT</b> : at least one component is in its HOT period (you do not have too must time to think to upgrade to a newer version)</li></ul>");
		applicationObsolescenceKpiDao.save(o);
	}
}
