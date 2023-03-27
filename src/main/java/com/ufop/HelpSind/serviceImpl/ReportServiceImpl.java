package com.ufop.HelpSind.serviceImpl;

import com.ufop.HelpSind.dao.ReportDao;
import com.ufop.HelpSind.domain.Report;
import com.ufop.HelpSind.service.ReportService;
import com.ufop.HelpSind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@Transactional
public class ReportServiceImpl implements ReportService {
	
	@Autowired
	private ReportDao reportDao;
	
	@Autowired
	private UserService userService;

	@Override
	public void save(Report entity) {

	}

	@Override
	public Report read(String id) {
		return reportDao.findById(id).orElse(null);
	}

	@Override
	public List<Report> list() {
		return reportDao.findAllByIdCondominiumOrderByExpenseExpirationDateDesc(userService.logged().getCondominium().getIdCondominium());
	}

	@Override
	public Page<Report> listPage(Pageable page) {
		return reportDao.findAllByIdCondominiumOrderByExpenseExpirationDateDesc(userService.logged().getCondominium().getIdCondominium(), page);
	}

	@Override
	public void update(Report entity) {

	}

	@Override
	public void delete(Report entity) {

	}

	@Override
	public void validate(Report entity, BindingResult validation) {

	}
}
