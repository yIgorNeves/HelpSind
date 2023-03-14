package com.ufop.HelpSind.domain;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@SuppressWarnings("serial")
@Entity
@Table(name = "apartment")
public class Apartment implements Serializable, Comparable<Apartment> {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idapartment")
	private Long idApartment;
	
	private Integer number;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominium")
	private Condominium condominium;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idperson")
	private Person person;

	public Long getIdApartment() {
		return idApartment;
	}

	public void setIdApartment(Long idApartment) {
		this.idApartment = idApartment;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	public Condominium getCondominium() {
		return condominium;
	}

	public void setCondominium(Condominium condominium) {
		this.condominium = condominium;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	@Override
	public int compareTo(Apartment ap) {
		return this.toString().compareTo(ap.toString());
	}

}
