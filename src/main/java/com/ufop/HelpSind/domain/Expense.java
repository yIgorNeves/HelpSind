package com.ufop.HelpSind.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
	
	@NotNull
	@Min(0)
	@Column(name = "value")
	private BigDecimal value;
	
	@Enumerated(EnumType.STRING)
	private PaymentSituation situation;
	
	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idapartment")
	private Apartment apartment;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominium")
	private Condominium condominium;
	
	@Min(0)
	@NotNull
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

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
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

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	@Override
	public int compareTo(Expense o) {
		// TODO Auto-generated method stub
		return 0;
	}

}