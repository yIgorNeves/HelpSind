package com.ufop.HelpSind.controller;

import com.ufop.HelpSind.domain.Apartment;
import com.ufop.HelpSind.domain.ExpenseType;
import com.ufop.HelpSind.enums.PaymentSituation;
import com.ufop.HelpSind.enums.Status;
import com.ufop.HelpSind.service.ApartmentService;
import com.ufop.HelpSind.service.ExpenseTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("trustee/expenseType")
public class ExpenseTypeController {
	
	@Autowired
	private ExpenseTypeService expenseTypeService;
	
	@Autowired
	ApartmentService apartmentService;
	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] {"finance", "expenseTypes"};
	}
	

	
	@ModelAttribute("statusList")
	public List<Status> statusList() {
		return Arrays.asList(Status.values());
	}
	
	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getExpenseTypes(@RequestParam("expenseType") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, ModelMap model) {
		model.addAttribute("expenseType",
				expenseTypeService.listPage(PageRequest.of(page.orElse(1) - 1, size.orElse(20))));
		model.addAttribute("content", "expenseTypeList");
		return new ModelAndView("layouts/trustee", model);
	}
	
	@GetMapping("/cadastro")
	public ModelAndView getExpenseTypesRegister(@ModelAttribute("expenseType") ExpenseType expenseType) {
		return new ModelAndView("layouts/trustee", "content", "expenseTypeRegister");
	}
	
	@GetMapping("/{idExpenseType}/cadastro")
	public ModelAndView getExpenseTypesRegister(@PathVariable("expenseType") Long idExpenseType, ModelMap model) {
		model.addAttribute("expenseType", expenseTypeService.read(idExpenseType));
		model.addAttribute("content", "expenseTypeRegister");
		return new ModelAndView("layouts/trustee", model);
	}
	
	@PostMapping("/cadastro")
	public ModelAndView postExpenseTypesRegister(@Valid @ModelAttribute("expenseType") ExpenseType expenseType, BindingResult validation) {
		expenseTypeService.validate(expenseType, validation);
		if (validation.hasErrors()) {
			expenseType.setIdExpenseType(null);
			return new ModelAndView("layouts/trustee", "content", "expenseTypeRegister");
		}
		expenseTypeService.save(expenseType);
		return new ModelAndView("redirect:/trustee/expenseType");
	}
	
	@PutMapping("/cadastro")
	public ModelAndView putExpenseTypesRegister(@Valid @ModelAttribute("idExpenseType") ExpenseType expenseType, BindingResult validation,
			ModelMap model) {
		expenseTypeService.validate(expenseType, validation);
		if (validation.hasErrors()) {
			model.addAttribute("content", "expenseTypeRegister");
			return new ModelAndView("layout/trustee", model);
		}
		expenseTypeService.update(expenseType);
		return new ModelAndView("redirect:/trustee/expenseType");
	}
	
	@DeleteMapping("/delete")
	public ModelAndView deleteExpenseTypesRegister(@RequestParam("idObj") Long idObj) {
		expenseTypeService.delete(expenseTypeService.read(idObj));
		return new ModelAndView();
	}
	
	
}
