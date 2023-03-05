package com.ufop.HelpSind.enums;

public enum AccountType {
	
	CX("Caixa"),
	BC("Conta Bancaria");
	
	private final String nome;

	private AccountType(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

}
