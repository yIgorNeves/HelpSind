package com.ufop.HelpSind.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.ufop.HelpSind.dao.ApartmentDao;
import com.ufop.HelpSind.domain.Apartment;
import com.ufop.HelpSind.service.ApartmentService;
import com.ufop.HelpSind.service.UserService;

@Service
@Transactional
public class ApartmentServiceImpl implements ApartmentService{
	
	@Autowired
	private ApartmentDao apartmentDao;
	
	@Autowired
	private UserService userService;

	@Override
	public void save(Apartment apartment) {
		if (apartment.getIdApartment() == null) {
			apartmentDao.save(apartment);
		}		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Apartment read(Long id) {
		return apartmentDao.findById(id).get();
	}

	@Override
	public List<Apartment> list() {
		return apartmentDao.findAllByCondominiumOrderByNumber(userService.logged().getCondominium());
	}

	@Override
	public Page<Apartment> listPage(Pageable page) {
		return apartmentDao.findAllByCondominiumOrderByNumber(userService.logged().getCondominium(), page);
	}

	@Override
	public void update(Apartment apartment) {
		apartmentDao.save(apartment);
	}

	@Override
	public void delete(Apartment apartment) {
		apartmentDao.delete(apartment);
	}

	@Override
	public void validate(Apartment apartment, BindingResult validation) {
		if (apartment.getIdApartment() == null) {
			if (apartmentDao.existsByNumber(apartment.getNumber())) {
				validation.rejectValue("number", "Unique");
			}
		} else {
			if (apartmentDao.existsByNumberAndIdApartmentNot(apartment.getNumber(), apartment.getIdApartment())) {
				validation.rejectValue("number", "Unique");
			}
		}
		
	}
}
