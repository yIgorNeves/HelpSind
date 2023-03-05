package com.ufop.HelpSind.service;

import com.ufop.HelpSind.domain.Condominium;

public interface CondominiumService extends CrudService<Condominium, Long>{

	public Condominium read();
	
}
