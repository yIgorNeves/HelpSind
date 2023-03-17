package com.ufop.HelpSind.serviceImpl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.ufop.HelpSind.dao.AccountDao;
import com.ufop.HelpSind.domain.Account;
import com.ufop.HelpSind.domain.Condominium;
import com.ufop.HelpSind.service.AccountService;
import com.ufop.HelpSind.service.UserService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {
	
	@Autowired
	private AccountDao accountDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void save(Account entity) {
		if(entity.getIdAccount() == null) {
			entity.setCurrentBalance(entity.getInitialBalance());
			accountDao.save(entity);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Account read(Long id) {
		return accountDao.findById(id).get();
	}

	@Override
	public List<Account> list() {
		Condominium condominium = userService.logged().getCondominium();
		if (condominium == null) {
			return new ArrayList<>();
		}
		return condominium.getAccounts();
	}

	@Override
	public Page<Account> listPage(Pageable page) {
		Condominium condominium = userService.logged().getCondominium();
		if(condominium == null) {
			return Page.empty(page);
		}
		return accountDao.findAllByCondominiumOrderByTypeAsc(condominium, page);
	}

	@Override
	public void update(Account entity) {
		Account old = read(entity.getIdAccount());
		entity.setCurrentBalance(
				old.getCurrentBalance().subtract(old.getInitialBalance()).add(entity.getInitialBalance()));
		accountDao.save(entity);
	}

	@Override
	public void delete(Account entity) {
		accountDao.delete(entity); 
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validate(Account entity, BindingResult validation) {
		if (entity.getIdAccount() == null) {
			if (accountDao.existsByTypeAndCondominium(entity.getType(), userService.logged().getCondominium())) {
				validation.rejectValue("sigla", "Unique");
			}
		}
		else {
			if (accountDao.existsByTypeAndCondominiumAndIdAccountNot(entity.getType(), userService.logged().getCondominium(), 
					entity.getIdAccount())) {
				validation.rejectValue("type", "Unique");
			}
		}
	}

	@Override
	public BigDecimal currentBalance() {
		Condominium condominium = userService.logged().getCondominium();
		if (condominium == null || condominium.getAccounts().isEmpty()) {
			return BigDecimal.ZERO.setScale(2);
		} else {
			return accountDao.sumCurrentBalanceByCondominium(condominium);
		}
	}

}
