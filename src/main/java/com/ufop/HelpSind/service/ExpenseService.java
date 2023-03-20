package com.ufop.HelpSind.service;

import java.math.BigDecimal;
import java.util.List;

import com.ufop.HelpSind.domain.Expense;

public interface ExpenseService extends CrudService<Expense, Long> {
	
	
		public BigDecimal inadimplencia();

	
		public List<Expense> listarInadimplencia();

}
