package com.ufop.HelpSind.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name="movement")
@Inheritance(strategy = InheritanceType.JOINED)
public class Movement implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idmovement")
	private Long idMonvement;
	
	@NotNull
	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	private LocalDate date;
	
	@NotNull
	@Min(0)
	private BigDecimal value;
	
	@Size(max = 20)
	private String document;
	
	@Size(max = 255)
	private String description;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcount")
	private Account account;
	
	private Boolean reduction;

	public Long getIdMonvement() {
		return idMonvement;
	}

	public void setIdMonvement(Long idMonvement) {
		this.idMonvement = idMonvement;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getDocument() {
		return document;
	}

	public void setDocument(String document) {
		this.document = document;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Account getCount() {
		return account;
	}

	public void setCount(Account account) {
		this.account = account;
	}

	public Boolean getReduction() {
		return reduction;
	}

	public void setReduction(Boolean reduction) {
		this.reduction = reduction;
	}
	
	
	
}
