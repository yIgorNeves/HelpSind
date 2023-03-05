package com.ufop.HelpSind.enums;

public enum BankAccountType {

	C("Conta Corrente"),
	P("Poupança");
	
	private final String name;

	private BankAccountType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
