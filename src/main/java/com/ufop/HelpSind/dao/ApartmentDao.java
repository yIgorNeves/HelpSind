package com.ufop.HelpSind.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ufop.HelpSind.domain.Apartment;

public interface ApartmentDao extends PagingAndSortingRepository<Apartment, Long>, CrudRepository<Apartment, Long>{
	
	Boolean existsByNumber(Integer number);

	Boolean existsByNumberAndIdApartmentNot(Integer number, Long idApartment);

}
