package com.bnpp.ism.dao.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.bnpp.ism.entity.user.User;

public interface UserDao extends CrudRepository<User, Long> {

	@Query(name = "SELECT p FROM User p WHERE p.name LIKE %:name%")
	Iterable<User> findByName(@Param("name") String name);
}
