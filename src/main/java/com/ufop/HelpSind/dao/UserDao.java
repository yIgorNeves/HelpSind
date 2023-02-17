package com.ufop.HelpSind.dao;

import com.ufop.HelpSind.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface UserDao extends PagingAndSortingRepository<User, Long>{
	
	User findOneByCpf (Integer cpf);
	
	Boolean exsitByCpf (Integer cpf);
	
	Boolean exsitByCpfAndId (Integer cpf, Long id);
}
