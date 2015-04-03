package com.bnpp.ism.technicalcomponents.application.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.technicalcomponents.application.dao.TechnicalComponentDao;
import com.bnpp.ism.technicalcomponents.application.model.TechnicalComponent;

@Service
public class TechnicalComponentService {
	@Autowired
	private TechnicalComponentDao dao;

	@Transactional
	public Iterable<TechnicalComponent> getAll() {
		return dao.findAll();
	}
}
