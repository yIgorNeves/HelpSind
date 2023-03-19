package com.ufop.HelpSind.dao;



import java.math.BigDecimal;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ufop.HelpSind.domain.Condominium;
import com.ufop.HelpSind.enums.BankAccountType;
import com.ufop.HelpSind.domain.Account;

public interface AccountDao extends PagingAndSortingRepository<Account, Long>, CrudRepository<Account, Long>{
	
	Page<Account> findAllByCondominiumOrderByTypeAsc(Condominium condominium, Pageable page);
		
	Boolean existsByTypeAndCondominium(BankAccountType type, Condominium condominium);
	
	Boolean existsByTypeAndCondominiumAndIdAccountNot(BankAccountType type, Condominium condominium, Long idCount);
	
	@Query("select sum(currentBalance) from #{#entityName} c where c.condominium = :condominium")
	BigDecimal sumCurrentBalanceByCondominium(@Param("condominium") Condominium condominium);

}
