package com.bnpp.ism.business.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.api.IApplicationManager;
import com.bnpp.ism.api.exchangedata.application.ApplicationVersionView;
import com.bnpp.ism.api.exchangedata.application.ApplicationView;
import com.bnpp.ism.api.exchangedata.component.TechnicalComponentView;
import com.bnpp.ism.dao.application.ApplicationDao;
import com.bnpp.ism.dao.application.ApplicationVersionDao;
import com.bnpp.ism.dao.component.TechnicalComponentDao;
import com.bnpp.ism.entity.application.Application;
import com.bnpp.ism.entity.application.ApplicationVersion;
import com.bnpp.ism.entity.component.TechnicalComponent;

@Service
public class ApplicationServiceManager implements IApplicationManager {
	@Autowired
	private ApplicationDao appDao;
	@Autowired
	private ApplicationVersionDao appVerDao;
	@Autowired
	private TechnicalComponentDao componentDao;

	@Autowired
	Mapper dozerBeanMapper;

	@Transactional
	@Override
	public List<ApplicationView> getApplications() {
		List<ApplicationView> ret = new ArrayList<ApplicationView>();
		Iterable<Application> apps = appDao.findAll();
		if (apps != null) {
			for (Application app : apps) {
				ret.add(dozerBeanMapper.map(app, ApplicationView.class));
			}
		}
		return (ret.size() == 0 ? null : ret);
	}

	@Transactional
	@Override
	public void setTechnicalComponents(ApplicationVersionView applicationVersion) {
		ApplicationVersion app = appVerDao.findOne(applicationVersion.getId());
		if (app != null) {
			if (app.getBuiltOn() != null)
				app.getBuiltOn().removeAllComponents();
			if (app.getCanRunOn() != null)
				app.getCanRunOn().removeAllComponents();

			if (applicationVersion.getBuiltOn() != null
					&& applicationVersion.getBuiltOn().getComponents() != null) {
				for (TechnicalComponentView c : applicationVersion.getBuiltOn()
						.getComponents()) {
					TechnicalComponent component = componentDao.findOne(c
							.getId());
					if (component != null) {
						app.addBuiltOnComponent(component);
					}
				}
			}
			if (applicationVersion.getCanRunOn() != null
					&& applicationVersion.getCanRunOn().getComponents() != null) {
				for (TechnicalComponentView c : applicationVersion
						.getCanRunOn().getComponents()) {
					TechnicalComponent component = componentDao.findOne(c
							.getId());
					if (component != null) {
						app.addCanRunOnComponent(component);
					}
				}
			}
			appVerDao.save(app);
		}
	}
}
