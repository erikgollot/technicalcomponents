package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.kpi.value.ApplicationVersionKpiSnapshotView;

public interface IApplicationVersionKpiSnapshotManager {		
	ApplicationVersionKpiSnapshotView createSnapshot(Long idApplicationVersion);
	List<ApplicationVersionKpiSnapshotView> getSnapshots(Long idApplicationVersion);
}
