package com.bnpp.ism.business.services;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.api.IKpiManager;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiListView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.KpiEnumLiteralView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualEnumKpiView;
import com.bnpp.ism.api.exchangedata.kpi.metadata.ManualNumericKpiView;
import com.bnpp.ism.dao.kpi.metadata.AbstractKpiDao;
import com.bnpp.ism.dao.kpi.metadata.KpiEnumLiteralDao;
import com.bnpp.ism.dao.kpi.value.KpiValueDao;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiEnumLiteral;
import com.bnpp.ism.entity.kpi.metadata.ManualEnumKpi;
import com.bnpp.ism.entity.kpi.metadata.ManualNumericKpi;
import com.bnpp.ism.entity.kpi.value.KpiValue;

@Service
public class KpiManager implements IKpiManager {
	@Autowired
	AbstractKpiDao kpiDao;
	@Autowired
	KpiEnumLiteralDao kpiEnumLiteralDao;
	@Autowired
	KpiValueDao kpiValueDao;
	@Autowired
	Mapper dozerBeanMapper;

	@Transactional
	@Override
	public AbstractKpiListView loadAllKpiDefinitions() {
		AbstractKpiListView kpis = new AbstractKpiListView();
		Iterable<AbstractKpi> alls = kpiDao.findAll();
		if (alls != null) {
			for (AbstractKpi kpi : alls) {
				kpis.addKpi(dozerBeanMapper.map(kpi, AbstractKpiView.class));
			}
		}
		return (kpis.getKpis()!=null ? kpis : null);
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
		entity.setShortName(kpi.getShortName());
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

			// Check if literal is not used
			checkLiteralNotUsed(en, entity.getValue(), entity.getName());

			en.removeLiteral(entity);
			kpiDao.save(en);
		}
	}

	private void checkLiteralNotUsed(ManualEnumKpi en, float value,
			String literalName) {
		Iterable<KpiValue> values = kpiValueDao.findValuesUsingKpi(en.getId(),
				value);
		if (values != null && values.iterator().hasNext()) {
			throw new RuntimeException("Literal " + literalName
					+ " is used for measurements");
		}
	}

	@Transactional
	@Override
	public void updateLiteral(KpiEnumLiteralView literal) {
		// TODO check value
		// Name can be changed
		KpiEnumLiteral entity = kpiEnumLiteralDao.findOne(literal.getId());
		if (entity != null) {
			ManualEnumKpi en = ((ManualEnumKpi) entity.getEnumKpi());
			// If value is not the same and value is already used, no update
			if (literal.getValue() != entity.getValue()) {

				checkLiteralNotUsed(en, entity.getValue(), entity.getName());
			}

			checkValueAndLiteralNameNotAlreadyExist(en, literal);

			dozerBeanMapper.map(literal, entity);
			kpiEnumLiteralDao.save(entity);
		}
	}

	private void checkValueAndLiteralNameNotAlreadyExist(ManualEnumKpi en,
			KpiEnumLiteralView literal) {
		for (KpiEnumLiteral eLiteral : en.getLiterals()) {
			// We do not test with itself
			if (literal.getId() != eLiteral.getId()
					&& eLiteral.getName().equals(literal.getName())) {
				throw new RuntimeException("The literal " + eLiteral.getName()
						+ " already exists");
			}
			if (literal.getId() != eLiteral.getId()
					&& eLiteral.getValue() == literal.getValue()) {
				throw new RuntimeException("The literal " + eLiteral.getName()
						+ " with value " + eLiteral.getValue()
						+ " already exists");
			}
		}
	}

	@Transactional
	@Override
	public KpiEnumLiteralView addLiteral(Long idKpi, KpiEnumLiteralView literal) {
		ManualEnumKpi entity = (ManualEnumKpi) kpiDao.findOne(idKpi);
		if (entity != null) {

			checkValueAndLiteralNameNotAlreadyExist(entity, literal);

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
			checkMinMax(entity, kpi.getMinValue(), kpi.getMaxValue());
			entity.setMinValue(kpi.getMinValue());
			entity.setMaxValue(kpi.getMaxValue());
			kpiDao.save(entity);
		}
	}

	/**
	 * No update if exist value in measurement < minValue or exist value in
	 * measurements > maxValue If minValue == null and before it was not null,
	 * no problem If maxValue == null and before it was not null, no problem
	 * 
	 * @param entity
	 * @param minValue
	 * @param maxValue
	 */
	private void checkMinMax(ManualNumericKpi entity, Float minValue,
			Float maxValue) {
		if (minValue != null) {
			Iterable<KpiValue> values = kpiValueDao.findValuesLessThan(
					entity.getId(), minValue);
			if (values != null && values.iterator().hasNext()) {
				throw new RuntimeException("There are smaller values than "
						+ minValue + " in measurements");
			}
		}
		if (maxValue != null) {
			Iterable<KpiValue> values = kpiValueDao.findValuesGreaterThan(
					entity.getId(), maxValue);
			if (values != null && values.iterator().hasNext()) {
				throw new RuntimeException("There are greater values than "
						+ maxValue + " in measurements");
			}
		}
	}

	@Transactional
	@Override
	public void deleteKpi(Long kpiId) {
		// TODO check
		kpiDao.delete(kpiId);
	}

}
