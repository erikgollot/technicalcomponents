package com.bnpp.ism.business.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.api.IKpiManager;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiConfigurationView;
import com.bnpp.ism.dao.kpi.application.AbstractKpiDao;
import com.bnpp.ism.dao.kpi.metadata.KpiConfigurationDao;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiConfiguration;

@Service
public class KpiManager implements IKpiManager {
	@Autowired
	AbstractKpiDao kpiDao;
	@Autowired
	KpiConfigurationDao kpiConfigurationDao;
	
	@Autowired
	Mapper dozerBeanMapper;

	@Transactional
	@Override
	public List<AbstractKpiView> loadAllKpiDefinitions() {
		List<AbstractKpiView> kpis = new ArrayList<AbstractKpiView>();
		Iterable<AbstractKpi> alls = kpiDao.findAll();
		if (alls != null) {
			for (AbstractKpi kpi : alls) {
				kpis.add(dozerBeanMapper.map(kpi, AbstractKpiView.class));
			}
		}
		return (kpis.size() > 0 ? kpis : null);
	}

	@Transactional
	@Override
	public KpiConfigurationView createNewConfiguration(String name) {
		Iterable<KpiConfiguration> all = kpiConfigurationDao.findAll();
		if (all!=null) {
			for (KpiConfiguration c : all) {
				if (name.endsWith(c.getName())) {
					// Error
				}
			}
		}
		KpiConfiguration newConf = new KpiConfiguration();
		newConf.setName(name);
		
		return dozerBeanMapper.map(newConf, KpiConfigurationView.class);
	}
}
