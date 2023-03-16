package com.ufop.HelpSind.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ufop.HelpSind.domain.Condominium;
import com.ufop.HelpSind.enums.State;
import com.ufop.HelpSind.service.CondominiumService;

@Controller
@RequestMapping({"trustee/condominium"})
public class CondominiumController {
	
	@Autowired
	private CondominiumService condominiumService;
	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] {"inicio", ""};
	}
	
	@ModelAttribute("states")
	public State[] states() {
		return State.values();
	}
	
	@GetMapping("/cadastro")
	public ModelAndView getCondominiumRegister(ModelMap model) {
		Condominium condominium = condominiumService.read();
		if (condominium != null) {
			model.addAttribute("condominium", condominium);
		} else {
			model.addAttribute("condominium", new Condominium());
		}
		model.addAttribute("content", "condominiumRegister");
		return new ModelAndView("layouts/trustee", model);
	}
	
	@PostMapping("/cadastro")
	public ModelAndView CondominiumRegister(@Valid @ModelAttribute("condominium") Condominium condominium, BindingResult validation) {
		condominiumService.validate(condominium, validation);
		if (validation.hasErrors()) {
			condominium.setIdCondominium(null);
			return new ModelAndView("layouts/trustee", "content", "condominiumRegister");
		}	
		condominiumService.save(condominium);
		return new ModelAndView("redirect:/home");
	}
	
	@PutMapping("/cadastro")
	public ModelAndView putCondominioCadastro(@Valid @ModelAttribute("condominium") Condominium condominium, BindingResult validacao) {
		condominiumService.validate(condominium, validacao);
		if (validacao.hasErrors()) {
			return new ModelAndView("layouts/trustee", "content", "condominiumRegister");
		}
		condominiumService.update(condominium);
		return new ModelAndView("redirect:/home");
	}
	
	
}
