package com.ufop.HelpSind.dao;

import com.ufop.HelpSind.domain.Condominium;
import com.ufop.HelpSind.domain.ExpenseType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ExpenseTypeDao extends PagingAndSortingRepository<ExpenseType, Long>, CrudRepository<ExpenseType, Long> {

	Page<ExpenseType> findAllByCondominium(Condominium condominium, Pageable page);
}
