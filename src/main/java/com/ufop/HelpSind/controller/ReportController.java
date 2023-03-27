package com.ufop.HelpSind.controller;

import com.ufop.HelpSind.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Controller
@RequestMapping("trustee/report")
public class ReportController {

	@Autowired
	private ReportService reportService;


	@GetMapping({ "", "/"  })
	public ModelAndView getReport(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size,
			ModelMap model) {
		model.addAttribute("report", reportService.listPage(PageRequest.of(page.orElse(1) - 1, size.orElse(Integer.MAX_VALUE))));
		model.addAttribute("content", "reportList");
		return new ModelAndView("layouts/trustee", model);
	}



	
	
}
