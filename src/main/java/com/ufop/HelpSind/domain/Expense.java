package com.ufop.HelpSind.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.ufop.HelpSind.enums.ExpenseType;
import org.springframework.format.annotation.DateTimeFormat;

import com.ufop.HelpSind.enums.PaymentSituation;

@SuppressWarnings("serial")
@Entity
@Table(name = "expenses")
public class Expense implements Serializable, Comparable<Expense>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idexpense")
	private Long idExpense;
	
	@Column(name = "name")
	private String name;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "issuancedate")
	private LocalDate issuanceDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "expirationdate")
	private LocalDate expirationDate;
	
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@Column(name = "receivingdate")
	private LocalDate receivingDate;
	
	@Enumerated(EnumType.STRING)
	private PaymentSituation situation;

	@Column(name = "expense_type")
	@Enumerated(EnumType.STRING)
	private ExpenseType typeEnum;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idapartment")
	private Apartment apartment;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominium")
	private Condominium condominium;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idexpensetype")
	private com.ufop.HelpSind.domain.ExpenseType expenseType;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "expense_apartment_reading", joinColumns = @JoinColumn(name = "idexpense"), inverseJoinColumns = @JoinColumn(name = "id_apartment_reading"))
	private Set<ApartmentReading> apartmentReadingSet = new HashSet<>();

	@Transient
	private List<ApartmentReading> apartmentReadingList = new ArrayList<>();

	@NotNull
	@Min(0)
	private BigDecimal total;

	public Long getIdExpense() {
		return idExpense;
	}

	public void setIdExpense(Long idExpense) {
		this.idExpense = idExpense;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getIssuanceDate() {
		return issuanceDate;
	}

	public void setIssuanceDate(LocalDate issuanceDate) {
		this.issuanceDate = issuanceDate;
	}

	public LocalDate getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDate expirationDate) {
		this.expirationDate = expirationDate;
	}

	public LocalDate getReceivingDate() {
		return receivingDate;
	}

	public void setReceivingDate(LocalDate receivingDate) {
		this.receivingDate = receivingDate;
	}

	public PaymentSituation getSituation() {
		return situation;
	}

	public void setSituation(PaymentSituation situation) {
		this.situation = situation;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	public Condominium getCondominium() {
		return condominium;
	}

	public void setCondominium(Condominium condominium) {
		this.condominium = condominium;
	}

	public com.ufop.HelpSind.domain.ExpenseType getExpenseType() {
		return expenseType;
	}

	public void setExpenseType(com.ufop.HelpSind.domain.ExpenseType expenseType) {
		expenseType = expenseType;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public ExpenseType getTypeEnum() {
		return typeEnum;
	}

	public void setTypeEnum(ExpenseType typeEnum) {
		this.typeEnum = typeEnum;
	}

	public Set<ApartmentReading> getApartmentReadingSet() {
		return apartmentReadingSet;
	}

	public void setApartmentReadingSet(Set<ApartmentReading> apartmentReadingSet) {
		this.apartmentReadingSet = apartmentReadingSet;
	}

	public List<ApartmentReading> getApartmentReadingList() {
		return apartmentReadingList;
	}

	public void setApartmentReadingList(List<ApartmentReading> apartmentReadingList) {
		this.apartmentReadingList = apartmentReadingList;
	}

	@Override
	public int compareTo(Expense o) {
		// TODO Auto-generated method stub
		return 0;
	}

}