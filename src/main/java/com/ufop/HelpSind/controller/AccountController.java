package com.ufop.HelpSind.controller;

import java.math.BigDecimal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.ufop.HelpSind.domain.Account;
import com.ufop.HelpSind.enums.AccountType;
import com.ufop.HelpSind.enums.BankAccountType;
import com.ufop.HelpSind.service.AccountService;

@Controller
@RequestMapping("trustee/account")
public class AccountController {
	
	//@Autowired
	private AccountService accountService;
	
	@ModelAttribute("ativo")
	public String[] ativo() {
		return new String[] {"finance", "accounts"};
	}
	
	@ModelAttribute("types")
	public AccountType[] accountsType() {
		return AccountType.values();
	}
	
	@ModelAttribute("typesBankAccount")
	public BankAccountType[] bankAccountTypes() {
		return BankAccountType.values();
	}
	
	@GetMapping("/cadastrar")
	public ModelAndView getAccountRegister(@ModelAttribute("account") Account account, ModelMap model) {
		account.setInitialBalance(BigDecimal.ZERO);
		model.addAttribute("type","");
		model.addAttribute("content", "accountRegister");
		return new ModelAndView("layouts/trustee", model);
	}
	
	@DeleteMapping("/delete")
	public ModelAndView deleteAccountRegister(@RequestParam("idObj") Long idObj) {
		accountService.delete(accountService.read(idObj));
		return new ModelAndView();
	}

}
