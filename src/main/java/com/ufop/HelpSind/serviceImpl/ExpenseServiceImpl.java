package com.ufop.HelpSind.serviceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.ufop.HelpSind.dao.ExpenseDao;
import com.ufop.HelpSind.domain.Condominium;
import com.ufop.HelpSind.domain.Expense;
import com.ufop.HelpSind.enums.PaymentSituation;
import com.ufop.HelpSind.service.ExpenseService;
import com.ufop.HelpSind.service.UserService;

@Service
@Transactional
public class ExpenseServiceImpl implements ExpenseService {
	
	@Autowired
	private ExpenseDao expenseDao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void save(Expense expense) {
		if(expense.getIdExpense() == null) {
			standard(expense);
			expenseDao.save(expense);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Expense read(Long id) {
		return expenseDao.findById(id).get();
	}

	@Override
	public List<Expense> list() {
		Condominium condominium = userService.logged().getCondominium();
		if (condominium == null) {
			return new ArrayList<>();
		}
		return condominium.getExpense();
	}

	@Override
	public Page<Expense> listPage(Pageable page) {
		Condominium condominium = userService.logged().getCondominium();
		if (condominium == null) {
			return Page.empty(page);
		}
		return expenseDao.findAllByCondominiumOrderByIssuanceDateDescApartmentAsc(condominium, page);
	}

	@Override
	public void update(Expense expense) {
		standard(expense);
		expenseDao.save(expense);		
	}

	@Override
	public void delete(Expense expense) {
		expenseDao.delete(expense);
	}

	@Override
	public void validate(Expense expense, BindingResult validation) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public BigDecimal inadimplencia() {
		Condominium condominium = userService.logged().getCondominium();
		BigDecimal result;
		
		if(condominium == null) {
			result = BigDecimal.ZERO.setScale(2);
		} else {
			//result = expenseDao.sumValueByCondominiumAndExpirationDateBeforeAndReceivingDateIsNull(condominium, LocalDate.now());
			//descomentar de cima e comentar a debaixo
			result = new BigDecimal("35,2");
		}
		
		return result;
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Expense> listarInadimplencia() {
		Condominium condominium = userService.logged().getCondominium();
		List<Expense> exepenses = new ArrayList<>();
		
		if (condominium != null && !condominium.getExpense().isEmpty()) {
			exepenses.addAll(expenseDao.findAllByCondominiumAndExpirationDateBeforeAndReceivingDateIsNullOrderByApartmentAscExpirationDateAsc(
					condominium, LocalDate.now()));
		}
		
		return exepenses;
	}
	
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void standard(Expense expense) {
		if (expense.getIssuanceDate() == null) {
			expense.setIssuanceDate(LocalDate.now());
		}
		if (expense.getSituation() == null) {
			expense.setSituation(PaymentSituation.N);
		}
		if (expense.getCondominium() == null) {
			expense.setCondominium(userService.logged().getCondominium());
		}
	}

}
