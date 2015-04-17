package com.bnpp.ism.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.bnpp.ism.api.exchangedata.kpi.application.ApplicationObsolescenceKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.entity.kpi.application.ApplicationObsolescenceKpi;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualEnumKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualNumericKpi;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
public class GlobalAppConfig {

	// @Bean
	// @Primary
	// public ObjectMapper objectMapper() {
	// ObjectMapper objectMapper = new ObjectMapper();
	// objectMapper.registerSubtypes(AbstractKpi.class, AbstractKpiView.class,
	// ApplicationObsolescenceKpiView.class, ManualEnumKpiView.class,
	// ManualEnumKpiView.class, ApplicationObsolescenceKpi.class,
	// ManualNumericKpi.class, ManualEnumKpi.class);
	// return objectMapper;
	// }

	@Bean
	@Primary
	public MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter jsonConverter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.registerSubtypes(AbstractKpi.class, AbstractKpiView.class,
				ApplicationObsolescenceKpiView.class, ManualEnumKpiView.class,
				ManualEnumKpiView.class, ApplicationObsolescenceKpi.class,
				ManualNumericKpi.class, ManualEnumKpi.class);
		jsonConverter.setObjectMapper(objectMapper);
		return jsonConverter;
	}
}
