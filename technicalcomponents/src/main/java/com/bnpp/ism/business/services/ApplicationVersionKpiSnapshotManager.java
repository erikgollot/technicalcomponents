package com.bnpp.ism.business.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.api.IApplicationVersionKpiSnapshotManager;
import com.bnpp.ism.api.exchangedata.application.ApplicationKpiDashboard;
import com.bnpp.ism.api.exchangedata.kpi.value.ApplicationVersionKpiSnapshotView;
import com.bnpp.ism.api.exchangedata.kpi.value.KpiValueView;
import com.bnpp.ism.api.exchangedata.kpi.value.ManualUserMeasurementView;
import com.bnpp.ism.dao.application.ApplicationDao;
import com.bnpp.ism.dao.application.ApplicationVersionDao;
import com.bnpp.ism.dao.kpi.metadata.AbstractKpiDao;
import com.bnpp.ism.dao.kpi.value.ApplicationVersionKpiSnapshotDao;
import com.bnpp.ism.dao.kpi.value.ManualUserMeasurementDao;
import com.bnpp.ism.dao.user.UserDao;
import com.bnpp.ism.entity.application.Application;
import com.bnpp.ism.entity.application.ApplicationVersion;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.ApplicationVersionComputedKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;
import com.bnpp.ism.entity.kpi.value.ApplicationVersionKpiSnapshot;
import com.bnpp.ism.entity.kpi.value.KpiValue;
import com.bnpp.ism.entity.kpi.value.ManualUserMeasurement;

@Service
public class ApplicationVersionKpiSnapshotManager implements
		IApplicationVersionKpiSnapshotManager {
	@Autowired
	AbstractKpiDao kpiDao;
	@Autowired
	ApplicationVersionDao applicationVersionDao;
	@Autowired
	ApplicationVersionKpiSnapshotDao applicationVersionKpiSnapshotDao;
	@Autowired
	ManualUserMeasurementDao manualUserMeasurementDao;
	@Autowired
	ApplicationDao appDao;
	@Autowired
	UserDao userDao;

	@Autowired
	Mapper dozerBeanMapper;

	@Transactional
	@Override
	public ApplicationVersionKpiSnapshotView createSnapshot(
			Long idApplicationVersion) {
		ApplicationVersion app = applicationVersionDao
				.findOne(idApplicationVersion);
		if (app != null) {
			ApplicationVersionKpiSnapshot snapshot = new ApplicationVersionKpiSnapshot();
			snapshot.setForDate(new Date());
			snapshot.setFrozen(false);
			Set<AbstractKpi> kpis = getAuthorizedManualKpisAndComputedKpis(app);
			if (kpis != null) {
				for (AbstractKpi kpi : kpis) {
					snapshot.addKpi(kpi);
				}
			}
			app.addKpiSnapshot(snapshot);
			applicationVersionKpiSnapshotDao.save(snapshot);
			applicationVersionDao.save(app);

			return dozerBeanMapper.map(snapshot,
					ApplicationVersionKpiSnapshotView.class);
		} else {
			return null;
		}
	}

	// Only update date and frozen
	@Transactional
	@Override
	public ApplicationVersionKpiSnapshotView updateSnapshot(Long snapshotId,
			boolean frozen, Date forDate) {
		ApplicationVersionKpiSnapshot entity = applicationVersionKpiSnapshotDao
				.findOne(snapshotId);
		entity.setForDate(forDate);
		entity.setFrozen(frozen);
		applicationVersionKpiSnapshotDao.save(entity);
		return dozerBeanMapper.map(entity,
				ApplicationVersionKpiSnapshotView.class);
	}

	// Retrieve all manual 'application version' kpi, manual that are allowed
	// for this application version.
	// The rule is the following one :
	// For each manual KPI, we look in computed KPI is a KPI with the same name
	// exists :
	// - If no : add manual KPI to the list
	// - If yes : if the computed kpi 'canCompute' for this application version,
	// do not put manual kpi in the list, otherwise put manual KPI in the list
	private Set<AbstractKpi> getAuthorizedManualKpisAndComputedKpis(
			ApplicationVersion app) {
		Set<AbstractKpi> ret = new TreeSet<AbstractKpi>();
		Iterable<AbstractKpi> kpis = kpiDao.findAll();
		if (kpis != null && kpis.iterator().hasNext()) {
			List<ApplicationVersionComputedKpi> computed = new ArrayList<ApplicationVersionComputedKpi>();
			// Extract all application computed
			for (AbstractKpi kpi : kpis) {
				if (kpi instanceof ApplicationVersionComputedKpi
						&& kpi.isActive()) {
					computed.add((ApplicationVersionComputedKpi) kpi);
				}
			}
			// Take manuals for application version
			for (AbstractKpi kpi : kpis) {
				if (kpi.isActive()
						&& (kpi.getKind() == KpiKindEnum.MANUAL_APPLICATION_ENUM || kpi
								.getKind() == KpiKindEnum.MANUAL_APPLICATION_NUMERIC)) {
					if (!existActiveComputedWithSameName(kpi, computed, app)) {
						ret.add(kpi);
					}
				}
			}
			// Now add application version computed not already added
			for (ApplicationVersionComputedKpi kpi : computed) {
				if (kpi.canCompute(app)) {
					ret.add(kpi);
				}
			}
		}
		return (ret.size() == 0 ? null : ret);
	}

	private boolean existActiveComputedWithSameName(AbstractKpi kpi,
			List<ApplicationVersionComputedKpi> computed,
			ApplicationVersion applicationVersion) {
		for (ApplicationVersionComputedKpi c : computed) {
			if (kpi.getName().equals(c.getName())
					&& c.canCompute(applicationVersion)) {
				return true;
			}
		}
		return false;
	}

	@Transactional
	@Override
	public List<ApplicationVersionKpiSnapshotView> getSnapshots(
			Long idApplicationVersion) {
		ApplicationVersion app = applicationVersionDao
				.findOne(idApplicationVersion);
		if (app != null) {
			List<ApplicationVersionKpiSnapshotView> ret = new ArrayList<ApplicationVersionKpiSnapshotView>();
			if (app.getKpiSnapshots() != null) {
				for (ApplicationVersionKpiSnapshot s : app.getKpiSnapshots()) {
					ret.add(dozerBeanMapper.map(s,
							ApplicationVersionKpiSnapshotView.class));
				}
				return ret.size() == 0 ? null : ret;
			} else {
				return null;
			}
		} else {
			return null;
		}
	}

	@Transactional
	@Override
	public ManualUserMeasurementView createMeasurement(Long snapshotId,
			Long userId) {
		ApplicationVersionKpiSnapshot snapshot = applicationVersionKpiSnapshotDao
				.findOne(snapshotId);
		if (snapshot != null) {

			checkUserHasAlreadyAMeasurement(snapshot, userId);

			ManualUserMeasurement measurement = new ManualUserMeasurement();
			measurement.setCreationDate(new Date());
			measurement.setWho(userDao.findOne(userId));
			snapshot.addMeasurement(measurement);
			// Fill with empty values
			if (snapshot.getKpis() != null) {
				for (AbstractKpi kpi : snapshot.getKpis()) {
					if (kpi.getKind() == KpiKindEnum.MANUAL_APPLICATION_ENUM
							|| kpi.getKind() == KpiKindEnum.MANUAL_APPLICATION_NUMERIC) {
						KpiValue val = new KpiValue();
						val.setKpi(kpi);
						measurement.addValue(val);
					}
				}
			}
			manualUserMeasurementDao.save(measurement);
			applicationVersionKpiSnapshotDao.save(snapshot);
			return dozerBeanMapper.map(measurement,
					ManualUserMeasurementView.class);
		} else {
			throw new RuntimeException("Cannot find snapshot (id=" + snapshotId
					+ ")");
		}
	}

	private void checkUserHasAlreadyAMeasurement(
			ApplicationVersionKpiSnapshot snapshot, Long userId) {
		if (snapshot.getManualMeasurements() != null) {
			for (ManualUserMeasurement m : snapshot.getManualMeasurements()) {
				if (m.getWho().getId().longValue() == userId.longValue()) {
					throw new RuntimeException("This user ["
							+ m.getWho().getName()
							+ "] has already done a measurement");
				}
			}
		}
	}

	@Transactional
	@Override
	public ManualUserMeasurementView updateMeasurement(
			ManualUserMeasurementView measurement) {
		ManualUserMeasurement entity = manualUserMeasurementDao
				.findOne(measurement.getId());
		if (entity != null) {

			entity.setComments(measurement.getComments());
			entity.setWho(userDao.findOne(measurement.getWho().getId()));
			if (entity.getValues() != null) {
				for (KpiValue v : entity.getValues()) {
					KpiValueView newVal = findValue(v.getId(),
							measurement.getValues());
					if (newVal != null) {
						if (v.getKpi().acceptValue(newVal.getValue())) {
							v.setValue(newVal.getValue());
							v.setSet(true);
						} else {
							throw new RuntimeException("Value "
									+ newVal.getValue() + " not accepted");
						}
					}
				}
			}
			manualUserMeasurementDao.save(entity);
			return dozerBeanMapper.map(entity, ManualUserMeasurementView.class);
		} else {
			throw new RuntimeException("Cannot find measurement (id="
					+ measurement.getId() + "), at "
					+ measurement.getCreationDate().toString());
		}
	}

	private KpiValueView findValue(Long id, List<KpiValueView> values) {
		if (values != null) {
			for (KpiValueView v : values) {
				if (v.getId().equals(id))
					return v;
			}
			return null;
		} else
			return null;
	}

	@Transactional
	@Override
	public void deleteSnapshot(Long snapshotId) {
		// TODO check if authorized
		applicationVersionKpiSnapshotDao.delete(snapshotId);
	}

	@Transactional
	@Override
	public void deleteMeasurement(Long measurementId) {
		// TODO check if authorized
		ManualUserMeasurement measurement = manualUserMeasurementDao
				.findOne(measurementId);
		ApplicationVersionKpiSnapshot snapshot = measurement.getSnapshot();
		snapshot.removeMeasurement(measurement);
		manualUserMeasurementDao.delete(measurementId);
		applicationVersionKpiSnapshotDao.save(snapshot);
	}

	@Transactional
	@Override
	public ApplicationKpiDashboard getApplicationDashboard(Long applicationId) {
		Application app = appDao.findOne(applicationId);
		if (app != null) {
			if (app.getVersions() != null) {
				ApplicationKpiDashboard dashboard = DashboardHelper.create(app
						.getVersions());
				return dashboard;
			} else {
				return null;
			}
		} else {
			throw new RuntimeException("Cannot load application : "
					+ applicationId);
		}
	}

	@Transactional
	@Override
	public List<KpiValueView> computeAutomaticApplicationKpis(Long snapshotId) {
		ApplicationVersionKpiSnapshot snapshot = applicationVersionKpiSnapshotDao
				.findOne(snapshotId);
		List<KpiValueView> ret = new ArrayList<KpiValueView>();
		if (snapshot != null) {
			if (snapshot.getKpis() != null) {
				for (AbstractKpi kpi : snapshot.getKpis()) {
					if (kpi instanceof ApplicationVersionComputedKpi) {
						// canCompute is true if kpi is here
						ApplicationVersionComputedKpi computedKpi = (ApplicationVersionComputedKpi) kpi;
						KpiValue val = new KpiValue();
						val.setKpi(kpi);
						val.setValue(computedKpi.compute(snapshot
								.getApplicationVersion()));
						snapshot.addComputedKpiValue(val);
					}
				}
			}
			applicationVersionKpiSnapshotDao.save(snapshot);

		}
		if (snapshot.getComputedKpisValues() != null) {
			for (KpiValue val : snapshot.getComputedKpisValues()) {
				ret.add(dozerBeanMapper.map(val, KpiValueView.class));
			}
		}
		return (ret.size() == 0 ? null : ret);
	}

}
