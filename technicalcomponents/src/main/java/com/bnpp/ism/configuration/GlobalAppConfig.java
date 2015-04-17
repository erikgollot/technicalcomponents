package com.bnpp.ism.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.bnpp.ism.api.exchangedata.kpi.application.ApplicationObsolescenceKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.entity.kpi.application.ApplicationObsolescenceKpi;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualEnumKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualNumericKpi;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class GlobalAppConfig {

	@Bean
	@Primary
	public ObjectMapper objectMapper() {
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.registerSubtypes(AbstractKpi.class, AbstractKpiView.class,
				ApplicationObsolescenceKpiView.class, ManualEnumKpiView.class,
				ManualEnumKpiView.class, ApplicationObsolescenceKpi.class,
				ManualNumericKpi.class, ManualEnumKpi.class);
		return objectMapper;
	}
}
