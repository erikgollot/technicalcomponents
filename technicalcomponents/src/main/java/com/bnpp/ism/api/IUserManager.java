package com.bnpp.ism.api;

import java.util.List;

import com.bnpp.ism.api.exchangedata.user.UserView;

public interface IUserManager {
	List<UserView> getAllUsers();
	List<UserView> findByNameExtended(String name);
	List<UserView> findApplicationAuthorizedUsers(Long applicationId);
}
