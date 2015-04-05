package com.bnpp.ism.technicalcomponents.application.dao;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.technicalcomponents.application.model.storage.StoredFile;

public interface StoredFileDao extends CrudRepository<StoredFile, Long> {

}
