package com.bnpp.ism;

import java.util.Arrays;

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

import com.bnpp.ism.api.IComponentCatalogManager;

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
