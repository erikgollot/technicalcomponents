package com.bnpp.ism.dao.user;

import org.springframework.data.repository.CrudRepository;

import com.bnpp.ism.entity.user.User;


public interface UserDao extends CrudRepository<User, Long> {

}
