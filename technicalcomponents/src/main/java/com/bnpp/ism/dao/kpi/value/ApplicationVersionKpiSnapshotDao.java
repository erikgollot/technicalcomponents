package com.bnpp.ism.dao.kpi.value;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.kpi.value.ApplicationVersionKpiSnapshot;

public interface ApplicationVersionKpiSnapshotDao extends
		CrudRepository<ApplicationVersionKpiSnapshot, Long> {

}
