package com.bnpp.ism.business.services;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.dozer.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bnpp.ism.api.IUserManager;
import com.bnpp.ism.api.exchangedata.user.UserView;
import com.bnpp.ism.dao.user.UserDao;
import com.bnpp.ism.entity.user.User;

@Service
public class UserServiceManager implements IUserManager {

	@Autowired
	UserDao userDao;
	@Autowired
	Mapper dozerBeanMapper;

	@Transactional
	@Override
	public List<UserView> getAllUsers() {
		Iterable<User> all = userDao.findAll();
		List<UserView> ret = new ArrayList<UserView>();
		if (all != null) {
			for (User u : all) {
				ret.add(dozerBeanMapper.map(u, UserView.class));
			}
		}
		return ret.size() == 0 ? null : ret;
	}

	@Override
	public List<UserView> findByNameExtended(String name) {
		Iterable<User> all = userDao.findByName(name);
		List<UserView> ret = new ArrayList<UserView>();
		if (all != null) {
			for (User u : all) {
				ret.add(dozerBeanMapper.map(u, UserView.class));
			}
		}
		return ret.size() == 0 ? null : ret;
	}

	@Override
	public List<UserView> findApplicationAuthorizedUsers(Long applicationId) {
		// TODO - for now getAllUsers
		return getAllUsers();
	}
}
