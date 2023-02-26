package com.ufop.HelpSind.dao;

import com.ufop.HelpSind.domain.User;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<User, Long>, CrudRepository<User, Long>{
	
	User findOneByCpf (Integer cpf);
	
	Boolean existsUserByCpf (Integer cpf);
	
	Boolean existsUserByCpfAndId (Integer cpf, Long id);
}
