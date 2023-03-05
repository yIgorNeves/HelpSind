package com.ufop.HelpSind.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ufop.HelpSind.domain.Condominium;

public interface CondominiumDao  extends PagingAndSortingRepository<Condominium, Long>, CrudRepository<Condominium, Long>{
	
	Boolean existsByCnpj(String cnpj);
	
	Boolean existsByCnpjAndIdCondominiumNot(String cnpj, Long idCondominium);
	
}
