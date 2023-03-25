package com.ufop.HelpSind.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
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
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
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
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idapartment")
	private Apartment apartment;
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominium")
	private Condominium condominium;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idexpensetype")
	private com.ufop.HelpSind.domain.ExpenseType expenseType;

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "expense_apartment_reading", joinColumns = @JoinColumn(name = "idexpense"), inverseJoinColumns = @JoinColumn(name = "id_apartment_reading"))
	private Set<ApartmentReading> apartmentReadingSet = new HashSet<>();

	@Transient
	private List<ApartmentReading> apartmentReadingList = new ArrayList<>();

	@NotNull
	@Min(0)
	private BigDecimal total;

	@Transient
	private Boolean child = Boolean.FALSE;

	private BigDecimal lastMeasurement;

	private BigDecimal currentMeasurement;

	public Expense() {
	}

	public Expense(String name, LocalDate issuanceDate, LocalDate expirationDate, LocalDate receivingDate, PaymentSituation situation,
			ExpenseType typeEnum, Apartment apartment, Condominium condominium, com.ufop.HelpSind.domain.ExpenseType expenseType,
			BigDecimal total) {
		this.name = name;
		this.issuanceDate = issuanceDate;
		this.expirationDate = expirationDate;
		this.receivingDate = receivingDate;
		this.situation = situation;
		this.typeEnum = typeEnum;
		this.apartment = apartment;
		this.condominium = condominium;
		this.expenseType = expenseType;
		this.total = total;
	}

	public Expense(Expense expense, Apartment apartment, BigDecimal total) {
		this.name = this.getChildName(expense, apartment);
		this.issuanceDate = expense.getIssuanceDate();
		this.expirationDate = expense.getExpirationDate();
		this.receivingDate = expense.getReceivingDate();
		this.situation = expense.getSituation();
		this.typeEnum = expense.getTypeEnum();
		this.apartment = apartment;
		this.condominium = expense.getCondominium();
		this.expenseType = expense.getExpenseType();
		this.total = total;
		this.child = true;
	}
	public Expense(Expense expense, Apartment apartment, BigDecimal total , BigDecimal lastMeasurement, BigDecimal currentMeasurement) {
		this.name = this.getChildName(expense,apartment);
		this.issuanceDate = expense.getIssuanceDate();
		this.expirationDate = expense.getExpirationDate();
		this.receivingDate = expense.getReceivingDate();
		this.situation = expense.getSituation();
		this.typeEnum = expense.getTypeEnum();
		this.apartment = apartment;
		this.condominium = expense.getCondominium();
		this.expenseType = expense.getExpenseType();
		this.lastMeasurement = lastMeasurement;
		this.currentMeasurement = currentMeasurement;
		this.total = total;
		this.child = true;
	}

	

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
		this.expenseType = expenseType;
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

	public Boolean getChild() {
		return child;
	}

	public void setChild(Boolean child) {
		this.child = child;
	}

	public BigDecimal getLastMeasurement() {
		return lastMeasurement;
	}

	public void setLastMeasurement(BigDecimal lastMeasurement) {
		this.lastMeasurement = lastMeasurement;
	}

	public BigDecimal getCurrentMeasurement() {
		return currentMeasurement;
	}

	public void setCurrentMeasurement(BigDecimal currentMeasurement) {
		this.currentMeasurement = currentMeasurement;
	}

	@Override
	public int compareTo(Expense o) {
		// TODO Auto-generated method stub
		return 0;
	}

	@PrePersist
	void onPersist(){
		this.getApartmentReadingSet().forEach(each ->{
			each.setCondominium(this.getCondominium());
		});
	}

	private String getChildName(Expense expense, Apartment apartment){
		String name = expense.getName() + " (Apartamento: %s)";
		return String.format(name, apartment.getNumber());
	}

	@JsonProperty
	public String getTypeEnumComplete(){
		return ExpenseType.P.getSigla().equals(this.typeEnum.getSigla()) ?  "Proporcional" : "Igualitário";
	}

	@JsonProperty
	public String getSituationComplete(){
		if (PaymentSituation.P.getSigla().equals(this.situation.getSigla())){
			return "Pago";
		} else if (this.expirationDate != null && this.expirationDate.isBefore(LocalDate.now())) {
			return "Vencido";
		}else {
			return "Não Pago";
		}
	}
}