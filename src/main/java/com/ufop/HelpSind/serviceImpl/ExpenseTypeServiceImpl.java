package com.ufop.HelpSind.serviceImpl;

import com.ufop.HelpSind.dao.ExpenseTypeDao;
import com.ufop.HelpSind.domain.Condominium;
import com.ufop.HelpSind.domain.ExpenseType;
import com.ufop.HelpSind.enums.PaymentSituation;
import com.ufop.HelpSind.service.ExpenseService;
import com.ufop.HelpSind.service.ExpenseTypeService;
import com.ufop.HelpSind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class ExpenseTypeServiceImpl implements ExpenseTypeService {
	
	@Autowired
	private ExpenseTypeDao dao;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void save(ExpenseType expenseType) {
		if(expenseType.getIdExpenseType() == null) {
			standard(expenseType);
			dao.save(expenseType);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public ExpenseType read(Long id) {
		return dao.findById(id).get();
	}

	@Override
	public List<ExpenseType> list() {
		return (List<ExpenseType>) dao.findAll();
	}

	@Override
	public Page<ExpenseType> listPage(Pageable page) {
		Condominium condominium = userService.logged().getCondominium();
		if (condominium == null) {
			return Page.empty(page);
		}
		return dao.findAllByCondominium(condominium, page);
	}

	@Override
	public void update(ExpenseType expenseType) {
		standard(expenseType);
		dao.save(expenseType);		
	}

	@Override
	public void delete(ExpenseType expenseType) {
		dao.delete(expenseType);
	}

	@Override
	public void validate(ExpenseType expenseType, BindingResult validation) {
		// TODO Auto-generated method stub
		
	}



	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void standard(ExpenseType expenseType) {
		if (expenseType.getCondominium() == null) {
			expenseType.setCondominium(userService.logged().getCondominium());
		}
	}

}
