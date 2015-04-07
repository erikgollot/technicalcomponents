package com.bnpp.ism.technicalcomponents.application.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.bnpp.ism.technicalcomponents.application.model.storage.StorageManager;

@Configuration
public class GlobalAppConfig {

	@Bean
	public StorageManager storageManager() {
		return new StorageManager();
	}
}
