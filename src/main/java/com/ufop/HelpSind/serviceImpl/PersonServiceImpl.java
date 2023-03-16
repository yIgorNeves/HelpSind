package com.ufop.HelpSind.serviceImpl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.ufop.HelpSind.dao.PersonDao;
import com.ufop.HelpSind.domain.Condominium;
import com.ufop.HelpSind.domain.Person;
import com.ufop.HelpSind.service.PersonService;
import com.ufop.HelpSind.service.UserService;

@Service
@Transactional
public class PersonServiceImpl implements PersonService{
	
	@Autowired
	private PersonDao personDao;
	
	@Autowired
	private UserService userService;

	@Override
	public void save(Person person) {
		if (person.getIdPerson() == null) {
			person.setCondominium(userService.logged().getCondominium());
			personDao.save(person);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Person read(Long id) {
		return personDao.findById(id).get();
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public List<Person> list() {
		Condominium condominium = userService.logged().getCondominium();
		if (condominium == null) {
			return new ArrayList<Person>();
		}
		return condominium.getPeople();
	}

	@Override
	public Page<Person> listPage(Pageable page) {
		Condominium condominium = userService.logged().getCondominium();
		if (condominium == null) {
			return Page.empty(page);
		}
		return personDao.findAllByCondominiumOrderByName(condominium, page);
	}

	@Override
	public void update(Person person) {
		personDao.save(person);
	}

	@Override
	public void delete(Person person) {
		personDao.delete(person);		
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validate(Person person, BindingResult validation) {
		
		if(person.getIdPerson() == null) {
			if (person.getCpf() != null && personDao.existsByCpfAndCondominium(person.getCpf(), userService.logged().getCondominium())) {
				validation.rejectValue("cpf", "Unique");
			}
		} else {
			if (person.getCpf() != null && personDao.existsByCpfAndCondominiumAndIdPersonNot(person.getCpf(), userService.logged().getCondominium(), person.getIdPerson())) {
				validation.rejectValue("cpf", "Unique");
			}
		}
	}
	

}
