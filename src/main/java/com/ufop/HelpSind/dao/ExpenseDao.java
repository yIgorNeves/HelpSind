package com.ufop.HelpSind.dao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import com.ufop.HelpSind.enums.PaymentSituation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ufop.HelpSind.domain.Apartment;
import com.ufop.HelpSind.domain.Condominium;
import com.ufop.HelpSind.domain.Expense;

public interface ExpenseDao extends PagingAndSortingRepository<Expense, Long>, CrudRepository<Expense, Long> {
	
	Boolean existsByIssuanceDateAndApartmentAndCondominium(LocalDate issuanceDate, Apartment apartment, Condominium condominium);

	Boolean existsByIssuanceDateAndApartmentAndCondominiumAndIdExpenseNot(LocalDate issuanceDate, Apartment apartment, Condominium condominium, Long idExpense);

//	@Query("select sum(value) from #{#entityName} c where c.condominium = :condominium and c.receivingDate is null and c.expirationDate < :date")
//	BigDecimal sumValueByCondominiumAndExpirationDateBeforeAndReceivingDateIsNull(
//			@Param("condominium") Condominium condominium, @Param("date") LocalDate data);

	List<Expense> findAllByCondominiumAndExpirationDateBeforeAndReceivingDateIsNullOrderByApartmentAscExpirationDateAsc(
			Condominium condominium, LocalDate data);

	Page<Expense> findAllByCondominiumOrderByIssuanceDateDescApartmentAsc(Condominium condominium,
			Pageable page);

	Integer countAllByCondominiumAndSituationAndExpirationDateAfter(Condominium condominium, PaymentSituation situation, LocalDate expirationDate);
	Integer countAllByCondominiumAndSituationAndExpirationDateBefore(Condominium condominium, PaymentSituation situation, LocalDate expirationDate);
}
