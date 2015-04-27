package com.bnpp.ism.dao.kpi.value;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bnpp.ism.entity.kpi.value.KpiValue;


public interface KpiValueDao extends CrudRepository<KpiValue, Long> {

	@Query("SELECT kpi FROM KpiValue kpi WHERE kpi.id=:kpiId AND value=:value")
	Iterable<KpiValue> findValuesUsingKpi(@Param("kpiId")Long kpiId, @Param("value") float value);
	
	@Query("SELECT kpi FROM KpiValue kpi WHERE kpi.id=:kpiId AND value<:minValue")
	Iterable<KpiValue> findValuesLessThan(@Param("kpiId")Long kpiId,@Param("minValue")float minValue);
	
	@Query("SELECT kpi FROM KpiValue kpi WHERE kpi.id=:kpiId AND value>:maxValue")
	Iterable<KpiValue> findValuesGreaterThan(@Param("kpiId")Long kpiId,@Param("maxValue")float maxValue);
}
