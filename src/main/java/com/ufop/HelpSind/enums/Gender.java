package com.ufop.HelpSind.enums;

public enum Gender {
	
	M("Maculino"),
	F("Feminino"),
	O("Outro");
	
	private final String name;

	private Gender(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}
	
}
