package com.ufop.HelpSind.domain;

import org.hibernate.annotations.Immutable;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "reports")
@Immutable
public class Report implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "uuid", updatable = false, nullable = false)
	private String id;

	@Column(insertable = false, updatable = false)
	private Long idExpense;

	@Column(insertable = false, updatable = false)
	private String expenseName;

	@Column(insertable = false, updatable = false)
	private LocalDate expenseReceivingDate;

	@Column(insertable = false, updatable = false)
	private LocalDate expenseExpirationDate;

	@Column(insertable = false, updatable = false)
	private String type;

	@Column(insertable = false, updatable = false)
	private String expenseSituation;

	@Column(insertable = false, updatable = false)
	private BigDecimal total;

	@Column(insertable = false, updatable = false)
	private String expenseType;

	@Column(insertable = false, updatable = false)
	private Long idApartmentReading;

	@Column(insertable = false, updatable = false)
	private Long idApartment;

	@Column(insertable = false, updatable = false)
	private Integer apartmentNumber;

	@Column(insertable = false, updatable = false)
	private Long idPerson;

	@Column(insertable = false, updatable = false)
	private String personName;

	@Column(name="idcondominium" , insertable = false, updatable = false)
	private Long idCondominium;

	public Report() {
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIdExpense(Long idExpense) {
		this.idExpense = idExpense;
	}

	public void setExpenseName(String expenseName) {
		this.expenseName = expenseName;
	}

	public void setExpenseReceivingDate(LocalDate expenseReceivingDate) {
		this.expenseReceivingDate = expenseReceivingDate;
	}

	public void setExpenseExpirationDate(LocalDate expenseExpirationDate) {
		this.expenseExpirationDate = expenseExpirationDate;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setExpenseSituation(String expenseSituation) {
		this.expenseSituation = expenseSituation;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

	public void setExpenseType(String expenseType) {
		this.expenseType = expenseType;
	}

	public void setIdApartmentReading(Long idApartmentReading) {
		this.idApartmentReading = idApartmentReading;
	}

	public void setIdApartment(Long idApartment) {
		this.idApartment = idApartment;
	}

	public void setApartmentNumber(Integer apartmentNumber) {
		this.apartmentNumber = apartmentNumber;
	}

	public void setIdPerson(Long idPerson) {
		this.idPerson = idPerson;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}

	public void setIdCondominium(Long idCondominium) {
		this.idCondominium = idCondominium;
	}

	public String getId() {
		return id;
	}

	public Long getIdExpense() {
		return idExpense;
	}

	public String getExpenseName() {
		return notEmptyString(expenseName);
	}

	public LocalDate getExpenseReceivingDate() {
		return expenseReceivingDate;
	}

	public LocalDate getExpenseExpirationDate() {
		return expenseExpirationDate;
	}

	public String getType() {
		return notEmptyString(type);
	}

	public String getExpenseSituation() {
		return notEmptyString(expenseSituation);
	}

	public BigDecimal getTotal() {
		return total;
	}

	public String getExpenseType() {
		return notEmptyString(expenseType);
	}

	public Long getIdApartmentReading() {
		return idApartmentReading;
	}

	public Long getIdApartment() {
		return idApartment;
	}

	public Integer getApartmentNumber() {
		return apartmentNumber;
	}

	public Long getIdPerson() {
		return idPerson;
	}

	public String getPersonName() {
		return notEmptyString(personName);
	}

	public Long getIdCondominium() {
		return idCondominium;
	}

	public String notEmptyString(String value){
		return value == null || value.equals("") ? " - " : value;
	}
}