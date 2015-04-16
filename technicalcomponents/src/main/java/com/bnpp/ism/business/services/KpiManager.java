package com.bnpp.ism.business.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.api.IKpiManager;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.dao.kpi.application.AbstractKpiDao;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;

@Service
public class KpiManager implements IKpiManager {
	@Autowired
	AbstractKpiDao kpiDao;

	@Autowired
	Mapper dozerBeanMapper;

	@Transactional
	@Override
	public List<AbstractKpiView> loadAllKpiDefinitions() {
		List<AbstractKpiView> kpis = new ArrayList<AbstractKpiView>();
		Iterable<AbstractKpi> alls = kpiDao.findAll();
		if (alls != null) {
			for (AbstractKpi kpi : alls) {
				kpis.add(dozerBeanMapper.map(kpi,AbstractKpiView.class));
			}
		}
		return (kpis.size() > 0 ? kpis : null);
	}
}
