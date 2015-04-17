package com.bnpp.ism.dao.kpi.metadata;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;

public interface AbstractKpiDao extends CrudRepository<AbstractKpi, Long> {

}
