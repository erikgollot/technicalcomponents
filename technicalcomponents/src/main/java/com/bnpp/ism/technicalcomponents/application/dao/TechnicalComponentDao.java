package com.bnpp.ism.technicalcomponents.application.dao;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.technicalcomponents.application.model.TechnicalComponent;

public interface TechnicalComponentDao extends CrudRepository<TechnicalComponent,Long>{

}
