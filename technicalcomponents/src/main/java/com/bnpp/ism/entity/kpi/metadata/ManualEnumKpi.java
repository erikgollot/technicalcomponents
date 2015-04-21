package com.bnpp.ism.entity.kpi.metadata;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

@Entity
@DiscriminatorValue("ManualEnumKpi")
public class ManualEnumKpi extends AbstractKpi implements KpiEnum {
	
	@OneToMany(mappedBy = "enumKpi", cascade = CascadeType.ALL,orphanRemoval=true)
	List<KpiEnumLiteral> literals;

	public List<KpiEnumLiteral> getLiterals() {
		return literals;
	}

	public KpiEnumLiteral getLiteral(String uid) {
		KpiEnumLiteral enumLiteral = null;
		if (getLiterals() != null) {
			for (KpiEnumLiteral en : literals) {
				if (en.getId().equals(uid)) {
					enumLiteral = en;
					break;
				}
			}
		}
		return enumLiteral;
	}

	public KpiEnumLiteral getLiteralWithValue(float value) {
		KpiEnumLiteral enumLiteral = null;
		if (getLiterals() != null) {
			for (KpiEnumLiteral en : literals) {
				if (value == en.getValue()) {
					enumLiteral = en;
					break;
				}
			}
		}
		return enumLiteral;
	}

	public void addLiteral(KpiEnumLiteral l) {
		if (getLiterals() == null) {
			literals = new ArrayList<KpiEnumLiteral>();
		}
		// force loading, hibernate bug
		getLiterals().size();
		getLiterals().add(l);
		l.setEnumKpi(this);
	}

	public void removeLiteral(KpiEnumLiteral l) {
		getLiterals().remove(l);
		l.setEnumKpi(null);
	}

	public float adjustValue(float value) {
		// Find the nearst value of the literals and change value to the value
		// of the literal founded
		KpiEnumLiteral enCurrent = null;
		float currentdistance = 0.0f;
		for (KpiEnumLiteral literal : getLiterals()) {
			if (enCurrent == null) {
				enCurrent = literal;
				currentdistance = Math.abs(literal.getValue() - value);
			} else {
				float distance = Math.abs(literal.getValue() - value);
				if (distance < currentdistance) {
					enCurrent = literal;
					currentdistance = distance;
				}
			}
		}
		return enCurrent.getValue();
	}

	
}
