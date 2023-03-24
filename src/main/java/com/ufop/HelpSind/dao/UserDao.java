package com.ufop.HelpSind.dao;

import com.ufop.HelpSind.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long>{
	
	User findByUsername (String username);
	
	Boolean existsByUsername(String username);
	
	Boolean existsByUsernameAndIdNot (String username, Long id);
	
	User findOneByUsername(String name);
}
