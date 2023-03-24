package com.ufop.HelpSind.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.ufop.HelpSind.domain.User;
import com.ufop.HelpSind.service.UserService;

import javax.validation.Valid;

@Controller
@RequestMapping("account")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] {"account", ""};
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView getTrusteeRegister(@ModelAttribute("trustee") User user) {
		return new ModelAndView("layouts/layoutSite", "content", "register");
	}
	
	@PostMapping("/cadastrar")
	public ModelAndView postTrusteeRegister(@Valid @ModelAttribute("trustee") User trustee, BindingResult validation) {
		trustee.setActive(true);
		userService.validate(trustee, validation);
		if (validation.hasErrors()) {
			trustee.setId(null);
			return new ModelAndView("layouts/layoutSite", "content","register");
		}	
		userService.saveTrustee(trustee);
		return new ModelAndView("redirect:/login?novo");
	}
	
	@GetMapping("/cadastro")
	public  ModelAndView getTrusteeRegister(ModelMap model) {
		model.addAttribute("trustee", userService.logged());
		model.addAttribute("content", "register");
		return new ModelAndView("layouts/layoutSite", model);
		
	}
	
	@PutMapping("/cadastro")
	public ModelAndView putTrusteeRegister(@Valid @ModelAttribute("trustee") User trustee, BindingResult validation) {
		userService.validate(trustee, validation);
		if (validation.hasErrors()) {
			trustee.setId(null);
			return new ModelAndView("layouts/layoutSite", "content","register");
		}
		
		userService.update(trustee);
		SecurityContextHolder.clearContext();
		return new ModelAndView("redirect:/login?novo");
	}
}
