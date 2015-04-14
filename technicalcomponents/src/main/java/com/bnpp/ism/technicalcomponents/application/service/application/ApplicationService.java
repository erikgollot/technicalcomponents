package com.bnpp.ism.technicalcomponents.application.service.application;

import java.util.List;

import com.bnpp.ism.technicalcomponents.application.model.view.application.ApplicationVersionView;

public interface ApplicationService {
	List<ApplicationVersionView> getApplications();
}
