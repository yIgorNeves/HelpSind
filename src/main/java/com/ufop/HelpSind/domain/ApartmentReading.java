package com.ufop.HelpSind.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@SuppressWarnings("serial")
@Entity
@Table(name = "apartment_reading")
public class ApartmentReading implements Serializable, Comparable<ApartmentReading> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_apartment_reading")
	private Long idApartmentReading;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_condominium")
	private Condominium condominium;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id_apartment")
	private Apartment apartment;

	private BigDecimal lastMeasurement;

	private BigDecimal currentMeasurement;


	@Override
	public int compareTo(ApartmentReading ap) {
		return this.toString().compareTo(ap.toString());
	}

	public ApartmentReading(Long idApartmentReading) {
		this.idApartmentReading = idApartmentReading;
	}

	public ApartmentReading() {
	}

	public ApartmentReading(Apartment apartment) {
		this.apartment = apartment;
	}

	public Long getIdApartmentReading() {
		return idApartmentReading;
	}

	public void setIdApartmentReading(Long idApartmentReading) {
		this.idApartmentReading = idApartmentReading;
	}

	public Condominium getCondominium() {
		return condominium;
	}

	public void setCondominium(Condominium condominium) {
		this.condominium = condominium;
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
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
}
