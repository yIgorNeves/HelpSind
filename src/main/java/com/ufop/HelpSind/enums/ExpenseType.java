package com.ufop.HelpSind.enums;

public enum ExpenseType {

	P("Proporcional", "P"),
	I("Igualitario", "I");

	private final String name;
	private final String sigla;

	public String getName() {
		return name;
	}
	public String getSigla(){return sigla;}

	private ExpenseType(String name, String sigla) {
		this.name = name;
		this.sigla = sigla;
	}
}
