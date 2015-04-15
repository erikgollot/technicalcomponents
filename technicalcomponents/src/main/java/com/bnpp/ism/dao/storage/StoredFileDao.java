package com.bnpp.ism.dao.storage;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.storage.StoredFile;

public interface StoredFileDao extends CrudRepository<StoredFile, Long> {

}
