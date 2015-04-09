package com.bnpp.ism.technicalcomponents.application.dao.component;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.technicalcomponents.application.model.component.TechnicalComponent;



public interface TechnicalComponentDao extends CrudRepository<TechnicalComponent,Long>{

}
