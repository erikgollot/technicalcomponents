package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.application.ApplicationVersionView;
import com.bnpp.ism.api.exchangedata.application.ApplicationView;

public interface IApplicationManager {
	List<ApplicationView> getApplications();

	void setTechnicalComponents(ApplicationVersionView applicationVersion);
}
