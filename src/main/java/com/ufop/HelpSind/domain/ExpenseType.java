package com.ufop.HelpSind.domain;

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
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
@Entity
@Table(name = "expense_type")
public class ExpenseType implements Serializable, Comparable<ExpenseType>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long idExpenseType;
	
	@Column(name = "name")
	private String name;

	@NotNull
	@Min(0)
	@Column(name = "value")
	private BigDecimal value;

	private String status;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominium")
	private Condominium condominium;

	public ExpenseType() {
	}

	public ExpenseType(Long idExpenseType) {
		this.idExpenseType = idExpenseType;
	}


	public Long getIdExpenseType() {
		return idExpenseType;
	}

	public void setIdExpenseType(Long idExpenseType) {
		this.idExpenseType = idExpenseType;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Condominium getCondominium() {
		return condominium;
	}

	public void setCondominium(Condominium condominium) {
		this.condominium = condominium;
	}

	@Override
	public int compareTo(ExpenseType o) {
		// TODO Auto-generated method stub
		return 0;
	}

}