package com.bnpp.ism.technicalcomponents.application.model.storage;

import java.util.List;

import com.bnpp.ism.technicalcomponents.application.service.storage.StorageStrategyAllocatorEnum;

public interface StorageSet {
	List<Storage> getStorages();

	Storage findBest(Long sizeToCreate);

	void setStrategyStorageAllocator(StorageStrategyAllocatorEnum strategy);

	StorageStrategyAllocatorEnum getStrategyStorageAllocator();
	
	void addStorage(Storage storage);
	
	void removeStorage(Storage s);
	
	Storage getStorageFromPrimaryKey(Long id);
}
