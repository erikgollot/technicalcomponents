package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.application.ApplicationVersionView;
import com.bnpp.ism.api.exchangedata.application.BuiltOnView;
import com.bnpp.ism.api.exchangedata.application.CanRunOnView;

public interface IApplicationManager {
	List<ApplicationVersionView> getApplications();

	void setTechnicalComponents(ApplicationVersionView applicationVersion);
}
