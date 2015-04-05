package com.bnpp.ism.technicalcomponents.application.component;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.technicalcomponents.application.dao.TechnicalComponentDao;
import com.bnpp.ism.technicalcomponents.application.model.TechnicalComponent;
import com.bnpp.ism.technicalcomponents.application.service.TechnicalComponentService;

@Service
public class TechnicalComponentServiceImpl implements TechnicalComponentService {
	@Autowired
	private TechnicalComponentDao dao;

	@Autowired Mapper dozerBeanMapper;
	
	@Transactional
	@Override
	public Iterable<TechnicalComponent> getAll() {
		return dao.findAll();
	}
}
