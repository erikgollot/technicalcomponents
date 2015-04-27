package com.bnpp.ism;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import com.bnpp.ism.api.IComponentCatalogManager;
import com.bnpp.ism.api.exchangedata.kpi.application.ApplicationObsolescenceKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@ComponentScan
@SpringBootApplication
@EnableCaching
@EnableAutoConfiguration
@Configuration
public class Application extends SpringBootServletInitializer {

	@Override
	protected SpringApplicationBuilder configure(
			SpringApplicationBuilder application) {
		return application.sources(Application.class);
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CacheManager cacheManager() {
		final SimpleCacheManager cacheManager = new SimpleCacheManager();
		cacheManager
				.setCaches(Arrays
						.asList(new ConcurrentMapCache(
								IComponentCatalogManager.SEARCH_COMPONENTS_FROM_FULL_PATHNAME_CACHE)));
		return cacheManager;
	}

	@Bean
	public CacheResolver cacheResolver() {
		final SimpleCacheResolver cacheResolver = new SimpleCacheResolver(
				cacheManager());
		return cacheResolver;
	}

	@Bean
	@Primary
	public MappingJackson2HttpMessageConverter customJackson2HttpMessageConverter() {
		MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.registerSubtypes(ManualEnumKpiView.class,
//				ManualNumericKpiView.class,
//				ApplicationObsolescenceKpiView.class);
		objectMapper.configure(
				DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);

		converter.setObjectMapper(objectMapper);
		return converter;
	}

	// @Bean
	// @Primary
	// public ObjectMapper customMapper() {
	// ObjectMapper objectMapper = new ObjectMapper();
	// objectMapper.registerSubtypes(AbstractKpi.class, AbstractKpiView.class,
	// ApplicationObsolescenceKpiView.class, ManualEnumKpiView.class,
	// ManualEnumKpiView.class, ApplicationObsolescenceKpi.class,
	// ManualNumericKpi.class, ManualEnumKpi.class);
	// objectMapper.configure(Serial);
	//
	//
	// config.
	//
	// return objectMapper;
	// }
}
