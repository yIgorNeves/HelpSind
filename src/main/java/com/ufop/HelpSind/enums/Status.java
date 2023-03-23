package com.ufop.HelpSind.enums;

public enum Status {

	A("Ativo", "A"),
	X("Excluido", "X");

	private final String name;
	private final String sigla;

	public String getName() {
		return name;
	}
	public String getSigla(){return sigla;}

	private Status(String name, String sigla) {
		this.name = name;
		this.sigla = sigla;
	}
}
