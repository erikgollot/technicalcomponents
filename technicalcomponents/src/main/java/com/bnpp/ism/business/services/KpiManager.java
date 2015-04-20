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
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiEnumLiteralView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiUsageView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView;
import com.bnpp.ism.dao.kpi.metadata.AbstractKpiDao;
import com.bnpp.ism.dao.kpi.metadata.KpiConfigurationDao;
import com.bnpp.ism.dao.kpi.metadata.KpiEnumLiteralDao;
import com.bnpp.ism.dao.kpi.metadata.KpiUsageDao;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiConfiguration;
import com.bnpp.ism.entity.kpi.metadata.KpiEnumLiteral;
import com.bnpp.ism.entity.kpi.metadata.KpiUsage;
import com.bnpp.ism.entity.kpi.metadata.ManualEnumKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualNumericKpi;

@Service
public class KpiManager implements IKpiManager {
	@Autowired
	AbstractKpiDao kpiDao;
	@Autowired
	KpiUsageDao kpiUsageDao;
	@Autowired
	KpiConfigurationDao kpiConfigurationDao;
	@Autowired
	KpiEnumLiteralDao kpiEnumLiteralDao;

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

	@Transactional
	@Override
	public KpiConfigurationView updateConfiguration(KpiConfigurationView config) {
		KpiConfiguration entity = kpiConfigurationDao.findOne(config.getId());
		if (entity != null) {
			entity.setName(config.getName());
			entity.setDescription(config.getDescription());
			if (config.getUsages() != null) {

				// First remove existing usages that are not used anymore
				List<KpiUsage> toRemove = new ArrayList<KpiUsage>();
				if (entity.getUsages() != null) {
					for (KpiUsage existing : entity.getUsages()) {
						boolean found = false;
						for (KpiUsageView usage : config.getUsages()) {
							if (existing.getId().equals(usage.getId())) {
								found = true;
							}
						}
						if (!found) {
							toRemove.add(existing);
						}
					}
					entity.getUsages().removeAll(toRemove);
				}
				for (KpiUsageView usage : config.getUsages()) {
					updateOrCreateUsage(entity, usage);
				}
			} else {
				// Remove all
				if (entity.getUsages() != null) {
					entity.getUsages().clear();
					// usages will deleted automatically because of removeOrphan
					// in KpiConfiguration
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

	@Transactional
	@Override
	public ManualEnumKpiView createEnumKpi(ManualEnumKpiView kpi) {
		ManualEnumKpi entity = dozerBeanMapper.map(kpi, ManualEnumKpi.class);
		this.kpiDao.save(entity);
		return dozerBeanMapper.map(entity, ManualEnumKpiView.class);
	}

	// Do not take literals
	@Transactional
	@Override
	public void updateEnumKpi(ManualEnumKpiView kpi) {
		ManualEnumKpi entity = (ManualEnumKpi) kpiDao.findOne(kpi.getId());
		if (entity != null) {
			copyAbstractKpiInfo(kpi, entity);
			kpiDao.save(entity);
		}
	}

	private void copyAbstractKpiInfo(AbstractKpiView kpi, AbstractKpi entity) {
		entity.setName(kpi.getName());
		entity.setDescription(kpi.getDescription());
		entity.setCategory(kpi.getCategory());
		entity.setActive(kpi.isActive());
	}

	@Transactional
	@Override
	public void deleteLiteral(Long literalId) {
		KpiEnumLiteral entity = kpiEnumLiteralDao.findOne(literalId);
		if (entity != null) {
			ManualEnumKpi en = ((ManualEnumKpi) entity.getEnumKpi());
			en.removeLiteral(entity);			
			kpiDao.save(en);
		}
	}

	@Transactional
	@Override
	public void updateLiteral(KpiEnumLiteralView literal) {
		// TODO check value
		// Name can be changed
		KpiEnumLiteral entity = kpiEnumLiteralDao.findOne(literal.getId());
		if (entity != null) {
			dozerBeanMapper.map(literal, entity);
			kpiEnumLiteralDao.save(entity);
		}
	}

	@Transactional
	@Override
	public KpiEnumLiteralView addLiteral(Long idKpi, KpiEnumLiteralView literal) {
		ManualEnumKpi entity = (ManualEnumKpi) kpiDao.findOne(idKpi);
		if (entity != null) {
			KpiEnumLiteral l = dozerBeanMapper.map(literal,
					KpiEnumLiteral.class);
			entity.addLiteral(l);
			this.kpiEnumLiteralDao.save(l);
			kpiDao.save(entity);
			return dozerBeanMapper.map(l, KpiEnumLiteralView.class);

		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public ManualNumericKpiView createNumericKpi(ManualNumericKpiView kpi) {
		ManualNumericKpi entity = dozerBeanMapper.map(kpi,
				ManualNumericKpi.class);
		this.kpiDao.save(entity);
		return dozerBeanMapper.map(entity, ManualNumericKpiView.class);
	}

	@Transactional
	@Override
	public void updateNumericKpi(ManualNumericKpiView kpi) {
		ManualNumericKpi entity = (ManualNumericKpi) kpiDao
				.findOne(kpi.getId());
		if (entity != null) {
			copyAbstractKpiInfo(kpi, entity);
			kpiDao.save(entity);
		}
	}

	@Transactional
	@Override
	public void deleteKpi(Long kpiId) {
		// TODO check
		kpiDao.delete(kpiId);
	}
}
