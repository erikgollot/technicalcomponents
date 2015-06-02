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

		// PF APA attributs
		TE01_Functional_Application_Size_In_PF();
		TE02_Technical_Application_Size();
		TE06_Applicative_Documentation();
		TE08_Conformity_Development_BestPractices();
		TE10_Conformity_Architecture_Norms();
		TE11_Conformity_Security_Norms();
		TE12_Conformity_Continuity_Norms();
		TE16_Conformity_Software_Policy();

		TE04_Reliability();
		TE13_Maintenability();
		TE14_Operability();
		DO01_Data_Quality();

		// KC2d !! TE03_2_Applicative_Obsolescence();
		FT03_Functional_Obsolescence();

		FT01_UI_Quality();
		FT04_Results_Reliability();
		FT06_Scalability();

		ME01_1_Security_Needs();
		ME01_2_Security_Needs();
		ME05_Business_Criticity();
		ME06_Business_Availability();
		ME07_Strategic_Importance();
	}

	private static final String Technical = "Technical";

	private void TE01_Functional_Application_Size_In_PF() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Functional Application Size in PF");
		kpi.setShortName("TE01");
		kpi.setDescription("Measure of the application size in <b>function points</b>. Retrieved from CAST tool.");
		kpi.setInt(false);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(Float.MAX_VALUE);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(Technical);

		abstractKpiDao.save(kpi);
	}

	private void TE02_Technical_Application_Size() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Technical Application Size");
		kpi.setShortName("TE01");
		kpi.setDescription("Size of appication in LOC");
		kpi.setInt(false);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(Float.MAX_VALUE);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(Technical);

		abstractKpiDao.save(kpi);
	}

	private static final String Business_Value = "Business Value";

	private void ME07_Strategic_Importance() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Strategic Importance");
		kpi.setShortName("ME07");
		kpi.setDescription("");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(Business_Value);

		abstractKpiDao.save(kpi);
	}

	private void ME06_Business_Availability() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Business Availability");
		kpi.setShortName("ME06");
		kpi.setDescription("Level of availabilty of the application.");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(Business_Value);

		abstractKpiDao.save(kpi);
	}

	private void ME05_Business_Criticity() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Business Criticity");
		kpi.setShortName("ME05");
		kpi.setDescription("Business Criticity of the application.<br><ul><li>1 = No business impact in case of unavailability</li><li>4 = Low business impact in case of unavailability</li><li>7 = Major business impact in case of unavailability,  visibility, serious, financial risk</li><li>10 = Business cannot works without this application</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(Business_Value);

		abstractKpiDao.save(kpi);
	}
	
	private void ME01_1_Security_Needs() {
		ManualEnumKpi kpi = new ManualEnumKpi();
		kpi.setName("Security Needs CIDT");
		kpi.setShortName("ME01-1");
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_ENUM);
		kpi.setDescription("CIDT classification");
		kpi.setCategory(technical_quality);

		KpiEnumLiteral low = new KpiEnumLiteral();
		low.setName("Low");
		low.setRank(1);
		low.setValue(1.0f);
		kpi.addLiteral(low);
		KpiEnumLiteral moderate = new KpiEnumLiteral();
		moderate.setName("Moderate");
		moderate.setRank(2);
		moderate.setValue(2.0f);
		kpi.addLiteral(moderate);
		KpiEnumLiteral serious = new KpiEnumLiteral();
		serious.setName("Serious");
		serious.setRank(3);
		serious.setValue(3.0f);
		kpi.addLiteral(serious);
		KpiEnumLiteral extrem = new KpiEnumLiteral();
		extrem.setName("Extrem");
		extrem.setRank(4);
		extrem.setValue(4.0f);
		kpi.addLiteral(extrem);
		abstractKpiDao.save(kpi);
	}
	
	private void ME01_2_Security_Needs() {
		ManualEnumKpi kpi = new ManualEnumKpi();
		kpi.setName("Security Needs ICP");
		kpi.setShortName("ME01-2");
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_ENUM);
		kpi.setDescription("ICP classification");
		kpi.setCategory(technical_quality);

		KpiEnumLiteral low = new KpiEnumLiteral();
		low.setName("Low");
		low.setRank(1);
		low.setValue(1.0f);
		kpi.addLiteral(low);
		KpiEnumLiteral moderate = new KpiEnumLiteral();
		moderate.setName("Moderate");
		moderate.setRank(2);
		moderate.setValue(2.0f);
		kpi.addLiteral(moderate);
		KpiEnumLiteral serious = new KpiEnumLiteral();
		serious.setName("Serious");
		serious.setRank(3);
		serious.setValue(3.0f);
		kpi.addLiteral(serious);
		KpiEnumLiteral extrem = new KpiEnumLiteral();
		extrem.setName("Extrem");
		extrem.setRank(4);
		extrem.setValue(4.0f);
		kpi.addLiteral(extrem);
		abstractKpiDao.save(kpi);
	}

	private static final String functional_quality = "Functional Quality";

	private void FT06_Scalability() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Scalability");
		kpi.setShortName("FT06");
		kpi.setDescription("Evaluation of the application scalability when new needs come.<br><ul><li>1 = Almost impossible to change the application</li><li>4 = Some changes can be done but it takes lot of time</li><li>7 = Some change can be done but it takes time</li><li>10 = Changing or adding new features are easy</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(functional_quality);

		abstractKpiDao.save(kpi);
	}

	private void FT04_Results_Reliability() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Results Reliability");
		kpi.setShortName("FT04");
		kpi.setDescription("Application results quality in terms of reliability and consistency.<br><ul><li>1 = Results are not reliable</li><li>4 = Results are not too much reliable, frequent checks are necessary</li><li>7 = Results are globally reliable and consistent</li><li>10 = We can trust on results</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(functional_quality);

		abstractKpiDao.save(kpi);
	}

	private void FT01_UI_Quality() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("UI Quality");
		kpi.setShortName("FT01");
		kpi.setDescription("UI quality perceived by its users.<br><ul><li>1 = Application is difficult to use. Lot of support is required</li><li>4 = Application is not intuitive. Frequent support is required</li><li>7 = Application is not totally intuitive but few support is required</li><li>10 = Application is easy to use</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(functional_quality);

		abstractKpiDao.save(kpi);
	}

	private static final String obsolescence = "Obscolescence";

	private void FT03_Functional_Obsolescence() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Functional Obsolescence");
		kpi.setShortName("FT03");
		kpi.setDescription("Compliance of the application to functional needs in the term.<br><ul><li>1 = Many functionalities are missing or application is no more in line with business needs</li><li>4 = Many functionalities are missing or application should change a lot to be in sync with business needs</li><li>7 = Very few functionalities or critical functionalities are missing</li><li>10 = Application has all functionalities and can evolve easily</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(obsolescence);

		abstractKpiDao.save(kpi);
	}

	// private void TE03_2_Applicative_Obsolescence() {
	// ManualNumericKpi kpi = new ManualNumericKpi();
	// kpi.setName("Applicative Obsolescence");
	// kpi.setShortName("TE03-2");
	// kpi.setDescription("");
	// kpi.setInt(true);
	// kpi.setMinValue(0.0f);
	// kpi.setMaxValue(10.0f);
	// kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
	// kpi.setCategory(obsolescence);
	//
	// abstractKpiDao.save(kpi);
	// }

	private static final String technical_quality = "Technical Quality";

	private void DO01_Data_Quality() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Data Quality");
		kpi.setShortName("DO01");
		kpi.setDescription("Quality of generated application data in terms of impacts on its functionalities.<br><ul><li>1 = Application data generate frequent problem of reliability and consistency, at least 70% of incidents are due to data errors</li><li>4 = Application data generate frequent problem of reliability and consistency, at least 50% of incidents are due to data errors</li><li>7 = Application data generate some problem of reliability and consistency, at least 30% of incidents are due to data errors</li><li>10 = Data modelis stable, less thant 10% of incidents are due to data errors</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(technical_quality);

		abstractKpiDao.save(kpi);
	}

	private void TE14_Operability() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Operability");
		kpi.setShortName("TE14");
		kpi.setDescription("Evaluation of the operability of the application.<br><ul><li>1 = Application required specific supervision. Many production interventions, application modifications often lead to incidents</li><li>4 = Application requires quite common intervention</li><li>7 = Few production intervention without major incident</li><li>10 = No specific supervision, production is stable and predictable</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(technical_quality);

		abstractKpiDao.save(kpi);
	}

	private void TE13_Maintenability() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Maintenability - Estimation");
		kpi.setShortName("TE13-2");
		kpi.setDescription("EStimation of the application maintenability.<br><ul><li>1 = Application is very complex and maintenance cost is high. Each update is risky</li><li>4 = Maintenance is difficult, but maintenance is controlled and predictable</li><li>7 = Few difficulties for maintenance,  but maintenance is controlled and predictable</li><li>10 = Bug fixes and enhancements are easily done or with few risk and cost</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(technical_quality);

		abstractKpiDao.save(kpi);
	}

	private void TE04_Reliability() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Reliability");
		kpi.setShortName("TE04");
		kpi.setDescription("Reliability of the application regarding number of incidents.<br><ul><li>1 = Application not stable, toom any incidents</li><li>4 = Application relatively not stable, some regular incidents, application need important follow-up</li><li>7 = Application relatively stable, some incidents</li><li>10 = Application is stable, very few incidents</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(technical_quality);

		abstractKpiDao.save(kpi);
	}

	private static final String conformity = "Conformity";

	private void TE16_Conformity_Software_Policy() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Conformity Software Policy");
		kpi.setShortName("TE16");
		kpi.setDescription("Conformity of application regarding Company Software Policiy.<br><ul><li>1 = Application does not comply with Software Policy</li><li>4 = Partial compliance, no more than 40% of Software Policy</li><li>7 = Partial compliance, no more than 70% of Software Policy</li><li>10 = Fully compliant</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(conformity);

		abstractKpiDao.save(kpi);
	}

	private void TE12_Conformity_Continuity_Norms() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Conformity Continuity Norms");
		kpi.setShortName("TE12");
		kpi.setDescription("Conformity of application regarding continuity norms.<br><ul><li>1 = Application does not comply with continuity norms</li><li>4 = Partial compliance, no more than 40% of continuity norms</li><li>7 = Partial compliance, no more than 70% of continuity norms</li><li>10 = Fully compliant</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(conformity);

		abstractKpiDao.save(kpi);
	}

	private void TE11_Conformity_Security_Norms() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Conformity Security Norms");
		kpi.setShortName("TE11");
		kpi.setDescription("Conformity of application regarding security norms.<br><ul><li>1 = Application does not comply with security norms</li><li>4 = Partial compliance, no more than 40% of security norms</li><li>7 = Partial compliance, no more than 70% of security norms</li><li>10 = Fully compliant</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(conformity);

		abstractKpiDao.save(kpi);
	}

	private void TE10_Conformity_Architecture_Norms() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Conformity Architecture Norms");
		kpi.setShortName("TE10");
		kpi.setDescription("Conformity of application regarding architecture norms.<br><ul><li>1 = No architecture document or application does not comply with architecture norms</li><li>4 = Partial compliance, no more than 40% of architecture norms</li><li>7 = Partial compliance, no more than 70% of architecture norms</li><li>10 = Fully compliant and have an architecture document</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(conformity);

		abstractKpiDao.save(kpi);
	}

	private void TE06_Applicative_Documentation() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Applicative Documentation");
		kpi.setShortName("TE06");
		kpi.setDescription("Completeness of the application documentation<br><ul><li>1 = Too few documents, 20% of required documents or 40% but not complete</li><li>4 = Only a short part of application documentation can be used, 20% - 40% of required documents exist or 60% exist but are not complete</li><li>7 = Most of the application documents can be used, at least 70% of required documents exist or 60% - 80% exist but are not complete</li><li>10 = Required application documentation exist and is regularely updated. At least 95% of required documents exist and are complete</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(conformity);

		abstractKpiDao.save(kpi);
	}

	private void TE08_Conformity_Development_BestPractices() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Conformity Development BestPractices");
		kpi.setShortName("TE08");
		kpi.setDescription("Maturity of application developments in terms of practices and norms.<br><ul><li>1 = No norms are really applied. No more than 20% of components comply</li><li>4 = Partial respect of norms. No more than 40% of components comply</li><li>7 = Most of the components comply. At least 70% comply</li><li>10 = Best practices are applied. At least 90% of components comply</li></ul>");
		kpi.setInt(true);
		kpi.setMinValue(0.0f);
		kpi.setMaxValue(10.0f);
		kpi.setKind(KpiKindEnum.MANUAL_APPLICATION_NUMERIC);
		kpi.setCategory(conformity);

		abstractKpiDao.save(kpi);
	}

	private static final String quality_risk = "Quality / Risk";
	private static final String cost_efficiency = "Cost efficiency";
	private static final String responsiveness = "Responsiveness";

	private void KC3_Incident_SLA_compliance() {
		ManualNumericKpi kpi = new ManualNumericKpi();
		kpi.setName("Incident SLA compliance");
		kpi.setShortName("KC3-Inc. SLA comp.");
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
		kpi.setShortName("KC2d-Obsoclescence");
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
		kpi.setShortName("KC2c-Cost comp.");
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
		kpi.setShortName("KC2b-stock res. var.");
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
		kpi.setShortName("KC2a-Inc. density");
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
		kpi.setShortName("KC1-Run cost. per fp");
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
		kpi.setShortName("KA2a-User satisf.");
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
		kpi.setShortName("Doc. quality");
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
