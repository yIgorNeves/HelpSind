package com.ufop.HelpSind.service;

import java.math.BigDecimal;
import java.util.List;

import com.ufop.HelpSind.domain.Expense;
import com.ufop.HelpSind.serviceImpl.ExpenseServiceImpl;

import static com.ufop.HelpSind.serviceImpl.ExpenseServiceImpl.*;

public interface ExpenseService extends CrudService<Expense, Long> {
	
	
		public BigDecimal inadimplencia();

	
		public List<Expense> listarInadimplencia();

		Totalizer getTotalizer();

}
