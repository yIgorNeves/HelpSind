package com.ufop.HelpSind.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
	private String CNPJ;
	
	@Email
	@Size(max = 100)
	private String email;
	
	@Size(max = 15)
	private String phone;
	
	@Size(max = 15)
	private String cellphone;
	
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

	public String getCNPJ() {
		return CNPJ;
	}

	public void setCNPJ(String cNPJ) {
		CNPJ = cNPJ;
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



	@Override
	public int compareTo(Condominium o) {
		return this.corporateName.compareTo(o.getCorporateName());
	}

}
