package com.bnpp.ism.configuration;

import java.text.SimpleDateFormat;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import com.bnpp.ism.api.exchangedata.kpi.application.ApplicationObsolescenceKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.entity.kpi.application.ApplicationObsolescenceKpi;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualEnumKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualNumericKpi;
import com.fasterxml.jackson.databind.ObjectMapper;

@Configuration
// @DependsOn("objectMapper")
public class JacksonAppConfig {

	// @Autowired
	// ObjectMapper mapper;

//	@Bean(name = "jackson")
//	public Jackson2ObjectMapperBuilder jacksonBuilder() {
//		Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
//		builder.indentOutput(true);
//		builder.dateFormat(new SimpleDateFormat("dd-mm-yyyy"));
//
//		ObjectMapper mapper = new ObjectMapper();
//		mapper.registerSubtypes(AbstractKpi.class, AbstractKpiView.class,
//				ApplicationObsolescenceKpiView.class, ManualEnumKpiView.class,
//				ManualEnumKpiView.class, ApplicationObsolescenceKpi.class,
//				ManualNumericKpi.class, ManualEnumKpi.class);
//
//		builder.configure(mapper);		
//		return builder;
//	}
}
