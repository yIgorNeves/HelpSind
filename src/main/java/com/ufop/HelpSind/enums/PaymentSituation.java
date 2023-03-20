package com.ufop.HelpSind.enums;

public enum PaymentSituation {
	
	P("Pago"),
	N("Não foi pago");
	
	private final String name;

	public String getName() {
		return name;
	}

	private PaymentSituation(String name) {
		this.name = name;
	}
}
