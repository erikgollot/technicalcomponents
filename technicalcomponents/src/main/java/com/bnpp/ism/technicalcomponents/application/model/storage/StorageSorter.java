package com.bnpp.ism.technicalcomponents.application.model.storage;

import java.util.Comparator;

public class StorageSorter implements Comparator<Storage> {

	@Override
	public int compare(Storage o1, Storage o2) {
		return (o1.getOrderInSet() - o2.getOrderInSet());
	}

}
