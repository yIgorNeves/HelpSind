package com.ufop.HelpSind.dao;

import com.ufop.HelpSind.domain.Report;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportDao extends PagingAndSortingRepository<Report, String>{

	Page<Report> findAllByIdCondominiumOrderByExpenseExpirationDateDesc(Long condominium, Pageable pagina);
	List<Report> findAllByIdCondominiumOrderByExpenseExpirationDateDesc(Long condominium);
}
