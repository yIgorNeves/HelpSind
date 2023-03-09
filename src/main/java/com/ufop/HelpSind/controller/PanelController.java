package com.ufop.HelpSind.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/trustee")
public class PanelController {
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] {"panel", ""};
	}
	
	@GetMapping({ "/", "", "/panel", "/dashboard" })
	public ModelAndView sindico(ModelMap model) {

	

		model.addAttribute("content", "panel");
		return new ModelAndView("layouts/trustee", model);
	}

}
