package com.ufop.HelpSind.domain;

import java.io.Serializable;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@SuppressWarnings("serial")
@Entity
@Table(name = "counts")
@Inheritance(strategy = InheritanceType.JOINED)
public class Count implements Serializable, Comparable<Count>{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "idcount")
	private Long idCount;
	
	@Size(min = 1, max = 2)
	@NotBlank
	private String type;
	
	@Size(max = 30)
	private String description;
	
	@Column(name = "initialbalance")
	private BigDecimal initialBalance;
	
	@Column(name = "currentBalance")
	private BigDecimal currentBalance;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idcondominium")
	private Condominium condominium;

	@Override
	public int compareTo(Count o) {
		// TODO Auto-generated method stub
		return 0;
	}

}
