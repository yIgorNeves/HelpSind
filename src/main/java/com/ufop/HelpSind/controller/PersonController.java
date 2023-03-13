package com.ufop.HelpSind.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.ufop.HelpSind.domain.Apartment;
import com.ufop.HelpSind.enums.Gender;
import com.ufop.HelpSind.enums.State;
import com.ufop.HelpSind.service.PersonService;

@Controller
@RequestMapping({"trustee/person", "trustee/condos"})/*condos = condominios em ingles*/
public class PersonController {

	@Autowired
	private PersonService personService;
	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "condominium", "condos" };
	}
	
	@ModelAttribute("genders")
	public Gender[] genders() {
		return Gender.values();
	}
	
	@ModelAttribute("apartments")
	public List<Apartment> aparments(){
		return null;
	}
	
	@ModelAttribute("state")
	public State[] state() {
		return State.values();
	}
	
}
