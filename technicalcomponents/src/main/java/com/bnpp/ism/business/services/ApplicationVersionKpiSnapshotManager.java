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
import com.bnpp.ism.api.exchangedata.kpi.metadata.AbstractKpiView;
import com.bnpp.ism.api.exchangedata.kpi.value.ApplicationVersionKpiSnapshotView;
import com.bnpp.ism.dao.application.ApplicationVersionDao;
import com.bnpp.ism.dao.kpi.metadata.AbstractKpiDao;
import com.bnpp.ism.dao.kpi.value.ApplicationVersionKpiSnapshotDao;
import com.bnpp.ism.entity.application.ApplicationVersion;
import com.bnpp.ism.entity.kpi.metadata.AbstractKpi;
import com.bnpp.ism.entity.kpi.metadata.ApplicationComputedKpi;
import com.bnpp.ism.entity.kpi.metadata.KpiKindEnum;
import com.bnpp.ism.entity.kpi.value.ApplicationVersionKpiSnapshot;

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
			Set<AbstractKpi> kpis = getAuthorizedManualKpis(app);
			if (kpis!=null) {
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

	// Retrieve all manual 'application version' kpi, manual that are allowed
	// for this application version.
	// The rule is the following one :
	// For each manual KPI, we look in computed KPI is a KPI with the same name
	// exists :
	// - If no : add manual KPI to the list
	// - If yes : if the computed kpi 'canCompute' for this application version,
	// do not put manual kpi in the list, otherwise put manual KPI in the list
	private Set<AbstractKpi> getAuthorizedManualKpis(ApplicationVersion app) {		
			Set<AbstractKpi> ret = new TreeSet<AbstractKpi>();
			Iterable<AbstractKpi> kpis = kpiDao.findAll();
			if (kpis != null && kpis.iterator().hasNext()) {
				List<ApplicationComputedKpi> computed = new ArrayList<ApplicationComputedKpi>();
				// Extract all application computed
				for (AbstractKpi kpi : kpis) {
					if (kpi instanceof ApplicationComputedKpi && kpi.isActive()) {
						computed.add((ApplicationComputedKpi) kpi);
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
				for (ApplicationComputedKpi kpi : computed) {
					if (kpi.canCompute(app)) {
						ret.add(kpi);
					}
				}
			}
			return (ret.size() == 0 ? null : ret);		
	}

	private boolean existActiveComputedWithSameName(AbstractKpi kpi,
			List<ApplicationComputedKpi> computed,
			ApplicationVersion applicationVersion) {
		for (ApplicationComputedKpi c : computed) {
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

}
