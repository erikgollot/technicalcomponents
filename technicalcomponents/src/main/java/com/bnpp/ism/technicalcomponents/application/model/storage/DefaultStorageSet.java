package com.bnpp.ism.technicalcomponents.application.model.storage;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.bnpp.ism.technicalcomponents.application.service.storage.StorageStrategyAllocatorEnum;

@Entity
public class DefaultStorageSet implements StorageSet {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column
	private StorageStrategyAllocatorEnum strategyStorageAllocator = StorageStrategyAllocatorEnum.ORDERED;

	@OneToMany
	(mappedBy="storageSet")
	private List<Storage> storages;

	@Override
	public List<Storage> getStorages() {
		return storages;
	}

	@Override
	public Storage findBest(Long sizeToCreate) {
		if (getStorages() == null || getStorages().size() == 0) {
			return null;
		} else {
			StorageStrategyAllocator allocator = StorageStrategyAllocator.strategies.get(getStrategyStorageAllocator());			
			return allocator.findBest(sizeToCreate, getStorages());
		}
	}

	@Override
	public void setStrategyStorageAllocator(StorageStrategyAllocatorEnum strategy) {
		this.strategyStorageAllocator = strategy;
	}

	@Override
	public StorageStrategyAllocatorEnum getStrategyStorageAllocator() {
		return this.strategyStorageAllocator;
	}

	public void addStorage(Storage storage) {
		if (getStorages() == null) {
			this.storages = new ArrayList<Storage>();
		}
		getStorages().add(storage);
	}

	public void removeStorage(Storage s) {
		getStorages().remove(s);
	}
}
