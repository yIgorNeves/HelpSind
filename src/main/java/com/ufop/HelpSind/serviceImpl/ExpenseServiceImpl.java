package com.ufop.HelpSind.serviceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.ufop.HelpSind.domain.Apartment;
import com.ufop.HelpSind.domain.ApartmentReading;
import com.ufop.HelpSind.enums.ExpenseType;
import com.ufop.HelpSind.service.ApartmentService;
import com.ufop.HelpSind.service.ExpenseTypeService;
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

	@Autowired
	private ApartmentService apartmentService;

	@Autowired
	private ExpenseTypeService expenseTypeService;
	@Override
	public void save(Expense expense) {
		if (expense.getIdExpense() == null) {
			standard(expense);
			Expense persisted = expenseDao.save(expense);

			new Thread(()->{
				this.creatExpensesForApartments(persisted);
			}).run();
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

		this.createApartmentReadingSet(expense);
	}

	@Transactional
	public void creatExpensesForApartments(Expense expense) {
		if(Boolean.TRUE.equals(expense.getChild())) return;
		var apartments = apartmentService.list();
		if (ExpenseType.I.getSigla().equalsIgnoreCase(expense.getTypeEnum().getSigla())) {

			BigDecimal total = expense.getTotal().divide(BigDecimal.valueOf(apartments.size()));
			for (Apartment apartment : apartments) {
				this.save(new Expense(expense, apartment, total));
			}

		}else if(expense.getApartment() == null && ExpenseType.P.getSigla().equalsIgnoreCase(expense.getTypeEnum().getSigla())){

			BigDecimal fixedValue = expense.getExpenseType().getValue(); //valor minimo da despesa

			BigDecimal sumAllLastMeansurement = expense.getApartmentReadingSet().stream().map(apartmentReading->apartmentReading.getLastMeasurement()).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);
			BigDecimal sumAllCurrentMeansurement = expense.getApartmentReadingSet().stream().map(apartmentReading->apartmentReading.getCurrentMeasurement()).reduce(BigDecimal::add).orElse(BigDecimal.ZERO);

			BigDecimal c30 = expense.getLastMeasurement().subtract(sumAllLastMeansurement);
			BigDecimal d30 = expense.getCurrentMeasurement().subtract(sumAllCurrentMeansurement);

			BigDecimal diffC30D30 = d30.subtract(c30);


			expense.getApartmentReadingSet().stream().forEach(apartmentReading -> {
				final BigDecimal diff = apartmentReading.getCurrentMeasurement().subtract(apartmentReading.getLastMeasurement());
				final BigDecimal total = (diffC30D30.divide(BigDecimal.valueOf(apartments.size()))).add(diff);
				final BigDecimal proporcionalValue = getFinalValue(total, fixedValue, expense);

				this.save(new Expense(expense, apartmentReading.getApartment(), proporcionalValue, apartmentReading.getLastMeasurement(), apartmentReading.getCurrentMeasurement()));
			});

		}
	}

	private BigDecimal getFinalValue(BigDecimal individualTotal, BigDecimal minValue, Expense expense){
		if (individualTotal.compareTo(BigDecimal.valueOf(5)) < 0){
			return minValue;
		}

		var expenseMeasurementDiff = (expense.getLastMeasurement().subtract(expense.getCurrentMeasurement()));

		if(expenseMeasurementDiff.compareTo(BigDecimal.ZERO)<0){
			expenseMeasurementDiff = expenseMeasurementDiff.multiply(BigDecimal.valueOf(-1));
		}

		return expense.getTotal().divide(expenseMeasurementDiff,2, RoundingMode.UP).multiply(individualTotal.setScale(2,RoundingMode.HALF_UP).subtract(BigDecimal.valueOf(5))).add(minValue).setScale(2, RoundingMode.HALF_UP);
	}


	private void createApartmentReadingSet(Expense expense) {

		if (ExpenseType.I.getSigla().equalsIgnoreCase(expense.getTypeEnum().getSigla())){
			return;
		}
		if (expense.getApartmentReadingList() == null || expense.getApartmentReadingList().isEmpty()) {
			return;
		}

		for (ApartmentReading apartmentReading : expense.getApartmentReadingList()) {
			apartmentReading.setCondominium(userService.logged().getCondominium());
			expense.getApartmentReadingSet().add(apartmentReading);
		}
	}

	@Override
	public Totalizer getTotalizer() {
		if(userService.logged() == null) return new Totalizer();
		Condominium condominium = userService.logged().getCondominium();
		Integer toPay = expenseDao.countAllByCondominiumAndSituationAndExpirationDateAfter(condominium,PaymentSituation.N,LocalDate.now().plusDays(1L));
		Integer paid = expenseDao.countAllByCondominiumAndSituationAndExpirationDateAfter(condominium,PaymentSituation.P,LocalDate.now().plusDays(1L));
		Integer overdue = expenseDao.countAllByCondominiumAndSituationAndExpirationDateBefore(condominium,PaymentSituation.N,LocalDate.now());
		return new Totalizer(toPay,paid,overdue);
	}



	public static class Totalizer {
		private Integer toPay;
		private Integer paid;
		private Integer overdue;

		public Totalizer() {
		}

		public Totalizer(Integer toPay, Integer paid, Integer overdue) {
			this.toPay = toPay;
			this.paid = paid;
			this.overdue = overdue;
		}

		public Integer getToPay() {
			return toPay;
		}

		public void setToPay(Integer toPay) {
			this.toPay = toPay;
		}

		public Integer getPaid() {
			return paid;
		}

		public void setPaid(Integer paid) {
			this.paid = paid;
		}

		public Integer getOverdue() {
			return overdue;
		}

		public void setOverdue(Integer overdue) {
			this.overdue = overdue;
		}
	}
}
