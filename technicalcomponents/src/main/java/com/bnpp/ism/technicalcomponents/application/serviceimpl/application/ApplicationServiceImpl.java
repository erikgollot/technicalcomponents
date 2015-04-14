package com.bnpp.ism.technicalcomponents.application.serviceimpl.application;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.technicalcomponents.application.dao.application.ApplicationVersionDao;
import com.bnpp.ism.technicalcomponents.application.model.application.ApplicationVersion;
import com.bnpp.ism.technicalcomponents.application.model.view.application.ApplicationVersionView;
import com.bnpp.ism.technicalcomponents.application.service.application.ApplicationService;

@Service
public class ApplicationServiceImpl implements ApplicationService {
	@Autowired
	private ApplicationVersionDao appDao;
	@Autowired
	Mapper dozerBeanMapper;
	
	@Transactional
	@Override
	public List<ApplicationVersionView> getApplications() {
		List<ApplicationVersionView> ret = new ArrayList<ApplicationVersionView>();
		Iterable<ApplicationVersion> apps = appDao.findAll();
		if (apps!=null) {
			for (ApplicationVersion app : apps) {
				ret.add(dozerBeanMapper.map(app, ApplicationVersionView.class));
			}
		}
		return (ret.size()==0 ? null : ret);
	}
}


