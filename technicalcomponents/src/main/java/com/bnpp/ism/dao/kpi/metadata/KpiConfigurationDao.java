package com.bnpp.ism.dao.kpi.metadata;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.kpi.metadata.KpiConfiguration;

public interface KpiConfigurationDao extends
		CrudRepository<KpiConfiguration, Long> {

}
