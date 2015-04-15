package com.bnpp.ism.dao.component;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.component.ComponentCategory;


public interface ComponentCategoryDao extends
		CrudRepository<ComponentCategory, Long> {

}
