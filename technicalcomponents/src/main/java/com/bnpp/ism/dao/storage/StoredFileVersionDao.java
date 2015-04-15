package com.bnpp.ism.dao.storage;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.storage.StoredFileVersion;

public interface StoredFileVersionDao extends CrudRepository<StoredFileVersion, Long> {

}
