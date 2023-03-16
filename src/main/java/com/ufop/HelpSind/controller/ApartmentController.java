package com.ufop.HelpSind.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ufop.HelpSind.domain.Apartment;
import com.ufop.HelpSind.domain.Person;
import com.ufop.HelpSind.service.ApartmentService;
import com.ufop.HelpSind.service.PersonService;

@Controller
@RequestMapping("trustee/apartments")
public class ApartmentController {
	
	//@Autowired
	private ApartmentService apartmentService;
	
	@Autowired
	private PersonService personService;
	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] { "condominium", "apartments" };
	}
	
	@ModelAttribute("people")
	public List<Person> people() {
		return personService.list();
	}
	
	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getApartments(@RequestParam("page") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, ModelMap model) {
		model.addAttribute("apartments",
				apartmentService.listPage(PageRequest.of(page.orElse(1) - 1, size.orElse(20))));
		model.addAttribute("content", "apartmentList");
		return new ModelAndView("layout/trustee", model);
	}
	
	@GetMapping("/cadastro")
	public ModelAndView getMoradiaCadastro(@ModelAttribute("apartments") Apartment apartment) {
		return new ModelAndView("layout/trustee", "content", "apartmentRegister");
	}
	
	@PostMapping("/cadastro")
	public ModelAndView postMoradiaCadastro(@Valid @ModelAttribute("apartments") Apartment apartment,
			BindingResult validation) {
		apartmentService.validate(apartment, validation);
		if (validation.hasErrors()) {
			apartment.setIdApartment(null);
			return new ModelAndView("layout/trustee", "content", "apartmentRegister");
		}
		apartmentService.save(apartment);
		return new ModelAndView("redirect:/trustee/apartments");
	}
	
	@PutMapping("/cadastro")
	public ModelAndView putMoradiaCadastro(@Valid @ModelAttribute("apartments") Apartment apartments, BindingResult validation,
			ModelMap model) {
		apartmentService.validate(apartments, validation);
		if (validation.hasErrors()) {
			model.addAttribute("content", "apartmentRegister");
			return new ModelAndView("layout/trustee", model);
		}
		apartmentService.update(apartments);
		return new ModelAndView("redirect:/trustee/apartments");
	}
	
	@DeleteMapping("/excluir")
	public ModelAndView deleteMoradiaCadastro(@RequestParam("idObj") Long idObj) {
		apartmentService.delete(apartmentService.read(idObj));
		return new ModelAndView("redirect:/trustee/apartments");
	}

}