package com.bnpp.ism.technicalcomponents.application.model.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class StorageStrategyAllocatorOrdered implements
		StorageStrategyAllocator {

	@Override
	public Storage findBest(Long sizeToCreate, List<Storage> storages) {
		List<Storage> sortedStorages = new ArrayList<Storage>(storages);
		Collections.sort(sortedStorages, new StorageSorter());

		for (Storage s : sortedStorages) {
			if (s.isActive()) {
				Long availableSpace = s.getAvailableDiskSpace();
				if (sizeToCreate<availableSpace - 100)
					return s;
			}
		}
		return null;
	}

}
