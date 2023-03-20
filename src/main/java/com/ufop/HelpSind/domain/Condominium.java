package com.ufop.HelpSind.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.validator.constraints.br.CNPJ;

import com.ufop.HelpSind.enums.State;

@SuppressWarnings("serial")
@Entity
@Table(name = "condominium")
public class Condominium implements Serializable, Comparable<Condominium>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcondominium")
	private Long idCondominium;
	
	@NotBlank
	@Size(min = 1, max =100)
	@Column(name = "corporatename")
	private String corporateName;
	
	@CNPJ
	private String cnpj;
	
	@Email
	@Size(max = 100)
	private String email;
	
	@Size(max = 15)
	private String phone;
	
	@Size(max = 15)
	private String cellphone;
	
	@Size(max = 30)
	@Column(name = "address")
	private String address;
	
	@NotBlank
	@Size(min = 1, max = 6)
	@Column(name = "addressnumber")
	private String addressNumber;

	@Size(max = 30)
	@Column(name = "addresscomplement")
	private String addressComplement;
	
	@NotBlank
	@Size(min = 1, max = 30)
	private String neighborhood;
	
	@NotBlank
	@Size(min = 1, max = 30)
	private String city;
	
	@NotBlank
	@Size(min = 8, max = 8)
	private String cep;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private State state;
	
	@OneToMany(mappedBy = "condominium", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "type")
	private List<Account> accounts = new ArrayList<>();

	@JsonIgnore
	@OneToOne(mappedBy = "condominium", fetch = FetchType.LAZY)
	private User trustee;

	@JsonIgnore
	@OneToMany(mappedBy = "condominium", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "name")
	private List<Person> people = new ArrayList<>();
	
	@OneToMany(mappedBy = "condominium", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE, orphanRemoval = true)
	@OrderBy(value = "issuanceDate desc, apartment, value")
	private List<Expense> expense = new ArrayList<>();

	public Condominium() {
	}

	public Condominium(Long idCondominium) {
		this.idCondominium = idCondominium;
	}

	public Long getIdCondominium() {
		return idCondominium;
	}

	public void setIdCondominium(Long idCondominium) {
		this.idCondominium = idCondominium;
	}

	public String getCorporateName() {
		return corporateName;
	}

	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getCellphone() {
		return cellphone;
	}

	public void setCellphone(String cellphone) {
		this.cellphone = cellphone;
	}

	public String getAddressNumber() {
		return addressNumber;
	}

	public void setAddressNumber(String addressNumber) {
		this.addressNumber = addressNumber;
	}

	public String getAddressComplement() {
		return addressComplement;
	}

	public void setAddressComplement(String addressComplement) {
		this.addressComplement = addressComplement;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public List<Account> getAccounts() {
		return accounts;
	}

	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public User getTrustee() {
		return trustee;
	}

	public void setTrustee(User trustee) {
		this.trustee = trustee;
	}

	public List<Person> getPeople() {
		return people;
	}

	public void setPeople(List<Person> people) {
		this.people = people;
	}

	public List<Expense> getExpense() {
		return expense;
	}

	public void setExpense(List<Expense> expense) {
		this.expense = expense;
	}

	@Override
	public int compareTo(Condominium o) {
		return this.corporateName.compareTo(o.getCorporateName());
	}

}
