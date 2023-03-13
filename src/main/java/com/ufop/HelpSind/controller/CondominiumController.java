package com.ufop.HelpSind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
	public ModelAndView getCondominiumCadastro(ModelMap model) {
		Condominium condominium = condominiumService.read();
		if (condominium != null) {
			model.addAttribute("codominium", condominium);
		} else {
			model.addAttribute("condominium", new Condominium());
		}
		model.addAttribute("content", "contentCadastro");
		return new ModelAndView("layout/layoutTrustee", model);
	}
}
