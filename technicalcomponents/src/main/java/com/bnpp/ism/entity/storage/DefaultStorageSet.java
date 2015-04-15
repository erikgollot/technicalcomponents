package com.bnpp.ism.entity.storage;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Version;

import com.bnpp.ism.api.StorageStrategyAllocatorEnum;

@Entity
public class DefaultStorageSet implements StorageSet {
	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Version
	private Long version;

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	@Enumerated(EnumType.STRING)
	@Column
	private StorageStrategyAllocatorEnum strategyStorageAllocator = StorageStrategyAllocatorEnum.ORDERED;

	@OneToMany(mappedBy = "storageSet", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
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
			StorageStrategyAllocator allocator = StorageStrategyAllocator.strategies
					.get(getStrategyStorageAllocator());
			return allocator.findBest(sizeToCreate, getStorages());
		}
	}

	@Override
	public void setStrategyStorageAllocator(
			StorageStrategyAllocatorEnum strategy) {
		this.strategyStorageAllocator = strategy;
	}

	@Override
	public StorageStrategyAllocatorEnum getStrategyStorageAllocator() {
		return this.strategyStorageAllocator;
	}

	@Override
	public void addStorage(Storage storage) {
		if (getStorages() == null) {
			this.storages = new ArrayList<Storage>();
		}
		getStorages().add(storage);
	}

	@Override
	public void removeStorage(Storage s) {
		getStorages().remove(s);
	}

	@Override
	public Storage getStorageFromPrimaryKey(Long id) {
		if (getStorages()!=null) {
			for (Storage s : getStorages()) {
				if (s.getId().equals(id))
					return s;
			}
		}
		return null;
	}
}
