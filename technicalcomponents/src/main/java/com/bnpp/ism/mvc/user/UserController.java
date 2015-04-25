package com.bnpp.ism.mvc.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.bnpp.ism.api.IUserManager;
import com.bnpp.ism.api.exchangedata.user.UserView;

@RestController
public class UserController {
	@Autowired
	private IUserManager service;

	@RequestMapping(value = "/service/allUsers", method = RequestMethod.GET, headers = "Accept=application/json")
	public @ResponseBody
	List<UserView> getAllUsers() {
		return service.getAllUsers();
	}

}
