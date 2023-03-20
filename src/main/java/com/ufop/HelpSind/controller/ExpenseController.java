package com.ufop.HelpSind.controller;

import java.time.LocalDate;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ufop.HelpSind.domain.Apartment;
import com.ufop.HelpSind.domain.Expense;
import com.ufop.HelpSind.enums.PaymentSituation;
import com.ufop.HelpSind.service.ApartmentService;
import com.ufop.HelpSind.service.ExpenseService;

@Controller
@RequestMapping("trustee/expense")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@Autowired
	ApartmentService apartmentService;
	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] {"finance", "expenses"};
	}
	
	@ModelAttribute("apartment")
	public List<Apartment> apartment(){
		return apartmentService.list();
	}
	
	@ModelAttribute("situation")
	public PaymentSituation[] paymentSituation() {
		return PaymentSituation.values();
	}
	
	@GetMapping({ "", "/", "/lista" })
	public ModelAndView getExpenses(@RequestParam("expense") Optional<Integer> page,
			@RequestParam("size") Optional<Integer> size, ModelMap model) {
		model.addAttribute("expense",
				expenseService.listPage(PageRequest.of(page.orElse(1) - 1, size.orElse(20))));
		model.addAttribute("content", "expenseList");
		return new ModelAndView("layouts/trustee", model);
	}
	
	@GetMapping("/cadastro")
	public ModelAndView getExpensesRegister(@ModelAttribute("expense") Expense expense) {
		expense.setIssuanceDate(LocalDate.now());
		return new ModelAndView("layouts/trustee", "content", "expenseRegister");
	}
	
	@GetMapping("/{idExpense}/cadastro")
	public ModelAndView getExpensesRegister(@PathVariable("expense") Long idExpense, ModelMap model) {
		model.addAttribute("expense", expenseService.read(idExpense));
		model.addAttribute("content", "expenseRegister");
		return new ModelAndView("layouts/trustee", model);
	}
	
	@PostMapping("/cadastro")
	public ModelAndView postExpensesRegister(@Valid @ModelAttribute("expense") Expense expense, BindingResult validation) {
		expenseService.validate(expense, validation);
		if (validation.hasErrors()) {
			expense.setIdExpense(null);
			return new ModelAndView("layouts/trustee", "content", "expenseRegister");
		}
		expenseService.save(expense);
		return new ModelAndView("redirect:/trustee/expense");
	}
	
	@PutMapping("/cadastro")
	public ModelAndView putExpensesRegister(@Valid @ModelAttribute("idExpense") Expense expense, BindingResult validation,
			ModelMap model) {
		expenseService.validate(expense, validation);
		if (validation.hasErrors()) {
			model.addAttribute("content", "expenseRegister");
			return new ModelAndView("layout/trustee", model);
		}
		expenseService.update(expense);
		return new ModelAndView("redirect:/trustee/expense");
	}
	
	@DeleteMapping("/delete")
	public ModelAndView deleteExpensesRegister(@RequestParam("idObj") Long idObj) {
		expenseService.delete(expenseService.read(idObj));
		return new ModelAndView();
	}
	
	
}
