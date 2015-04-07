package com.bnpp.ism.technicalcomponents.application.model.storage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class StorageStrategyAllocatorRandom implements
		StorageStrategyAllocator {

	@Override
	public Storage findBest(Long sizeToCreate, List<Storage> storages) {
		List<Storage> sortedStorages = new ArrayList<Storage>(storages);
		Collections.sort(sortedStorages, new StorageSorter());

		List<Storage> actives = new ArrayList<Storage>(storages);
		
		// Get active storage list with enough space
		for (Storage s : sortedStorages) {
			if (s.isActive() && (s.getAvailableDiskSpace()>(sizeToCreate+100))) {
				actives.add(s);
			}
		}
		
		// if have active storage, generates random number between 0 and list size
		if (actives.size()>0) {
			Random r = new Random();				
			int R = r.nextInt(actives.size());
			return actives.get(R);
		}
		return null;
	}

}
