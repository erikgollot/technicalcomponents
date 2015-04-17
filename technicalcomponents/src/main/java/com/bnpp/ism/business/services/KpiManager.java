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
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiUsageView;
import com.bnpp.ism.dao.kpi.metadata.AbstractKpiDao;
import com.bnpp.ism.dao.kpi.metadata.KpiConfigurationDao;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiConfiguration;
import com.bnpp.ism.entity.kpi.metadata.KpiUsage;

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
	public KpiConfigurationView createNewConfiguration(String name,
			String description) {
		Iterable<KpiConfiguration> all = kpiConfigurationDao.findAll();
		if (all != null) {
			for (KpiConfiguration c : all) {
				if (name.endsWith(c.getName())) {
					throw new RuntimeException("Name " + name
							+ " already exists");

				}
			}
		}
		KpiConfiguration newConf = new KpiConfiguration();
		newConf.setName(name);
		newConf.setDescription(description);
		kpiConfigurationDao.save(newConf);

		return dozerBeanMapper.map(newConf, KpiConfigurationView.class);
	}

	@Transactional
	@Override
	public List<KpiConfigurationView> getAllConfigurations() {
		Iterable<KpiConfiguration> all = kpiConfigurationDao.findAll();
		List<KpiConfigurationView> allView = new ArrayList<KpiConfigurationView>();
		if (all != null) {
			for (KpiConfiguration c : all) {
				allView.add(dozerBeanMapper.map(c, KpiConfigurationView.class));
			}
		}
		return allView;

	}

	@Override
	public KpiConfigurationView updateConfiguration(KpiConfigurationView config) {
		KpiConfiguration entity = kpiConfigurationDao.findOne(config.getId());
		if (entity != null) {
			entity.setName(config.getName());
			entity.setDescription(config.getDescription());
			if (config.getUsages() != null) {
				for (KpiUsageView usage : config.getUsages()) {
					updateOrCreateUsage(entity, usage);
				}
			}
			kpiConfigurationDao.save(entity);
			return dozerBeanMapper.map(entity, KpiConfigurationView.class);

		} else {
			throw new RuntimeException(
					"Cannot update configuration, configuration "
							+ config.getName() + " not found");
		}

	}

	private void updateOrCreateUsage(KpiConfiguration entity, KpiUsageView usage) {
		KpiUsage found = null;
		if (entity.getUsages() != null) {
			for (KpiUsage u : entity.getUsages()) {
				if (u.getId().equals(usage.getId()))
					found = u;
				break;
			}
		}
		if (found == null) {
			found = new KpiUsage();
			entity.addUsage(found);
		}
		found.setId(usage.getId());
		found.setMandatory(usage.isMandatory());
		found.setKpi(kpiDao.findOne(usage.getKpi().getId()));
	}
}
