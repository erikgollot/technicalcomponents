package com.bnpp.ism;

import java.util.Arrays;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCache;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.SimpleCacheResolver;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;

import com.bnpp.ism.api.IComponentCatalogManager;
import com.bnpp.ism.api.exchangedata.kpi.application.ApplicationObsolescenceKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.entity.kpi.application.ApplicationObsolescenceKpi;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualEnumKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualNumericKpi;
import com.fasterxml.jackson.databind.ObjectMapper;

@ComponentScan
@EnableAutoConfiguration
@EnableCaching
public class Application {
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
}
