package com.ufop.HelpSind.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.ufop.HelpSind.enums.Authorization;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.br.CPF;

@SuppressWarnings("serial")
@Entity
@Table(name = "users")
public class User implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(min=1, max=100)
	private String nome;
	
	@NotBlank
	@CPF
	@Size(min=11, max=11)
	private Integer cpf;
	
	@NotBlank
	@Size@Size(min=11, max=11)
	private Integer cellphone;
	
	@NotBlank
	@Size(min = 4, max = 100)
	private String password;
	
	@NotBlank
	@Size(min = 1, max = 100)
	@Email
	private String email;
	
	@ElementCollection(targetClass = Authorization.class)
	@CollectionTable(name="authorization", joinColumns = @JoinColumn(name="id_user"))
	@Enumerated(EnumType.STRING)
	@Column(name = "authorization")
	private Set<Authorization> auth = new HashSet<>();
	
	@NotNull
	private Boolean active;
	

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getCpf() {
		return cpf;
	}

	public void setCpf(Integer cpf) {
		this.cpf = cpf;
	}

	public Integer getCellphone() {
		return cellphone;
	}

	public void setCellphone(Integer cellphone) {
		this.cellphone = cellphone;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Authorization> getAuth() {
		return auth;
	}

	public void setAuth(Set<Authorization> auth) {
		this.auth = auth;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
	
	


}
