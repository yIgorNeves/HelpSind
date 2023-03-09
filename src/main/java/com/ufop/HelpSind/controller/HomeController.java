package com.ufop.HelpSind.controller;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] {"inicio", ""};
	}
	
	@GetMapping({"/home", "/", ""})
	public ModelAndView home() {
		return new ModelAndView("layouts/layoutSite", "content", "home");
	}
	
	@GetMapping({ "/entrar", "/login" })
	public ModelAndView preLogin() {
		return new ModelAndView("layouts/layoutSite", "content", "login");
	}
	
	@GetMapping("/auth")
	public String posLogin (Authentication authentication) {
		String retorno = "redirect:/entrar";
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		
		if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("TRUSTEE"))) {
			System.out.println("alou");
			retorno = "redirect:/trustee";
		} else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("TENANT"))) {
			retorno = "redirect:/tenant";
		} else if(userDetails.getAuthorities().contains(new SimpleGrantedAuthority("ADMIN"))) {
			retorno = "redirect:/admin";
		}
		
		return retorno;
	}
}
