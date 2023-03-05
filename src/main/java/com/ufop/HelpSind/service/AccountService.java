package com.ufop.HelpSind.service;

import java.math.BigDecimal;

import com.ufop.HelpSind.domain.Account;

public interface AccountService extends CrudService<Account, Long>{
	
	public BigDecimal currentBalance();

}
