package com.bnpp.ism.entity.storage;

import java.util.HashMap;
import java.util.List;

import com.bnpp.ism.api.StorageStrategyAllocatorEnum;

public interface StorageStrategyAllocator {
	Storage findBest(Long sizeToCreate, List<Storage> storages);

	public static final HashMap<StorageStrategyAllocatorEnum, StorageStrategyAllocator> strategies = new HashMap<StorageStrategyAllocatorEnum, StorageStrategyAllocator>() {
		{
			put(StorageStrategyAllocatorEnum.ORDERED,
					new StorageStrategyAllocatorOrdered());
			put(StorageStrategyAllocatorEnum.RANDOM,
					new StorageStrategyAllocatorRandom());
		}
	};
}
