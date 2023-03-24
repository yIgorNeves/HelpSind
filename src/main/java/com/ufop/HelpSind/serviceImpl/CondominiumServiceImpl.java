package com.ufop.HelpSind.serviceImpl;

import com.ufop.HelpSind.dao.CondominiumDao;
import com.ufop.HelpSind.domain.Condominium;
import com.ufop.HelpSind.domain.User;
import com.ufop.HelpSind.service.CondominiumService;
import com.ufop.HelpSind.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CondominiumServiceImpl implements CondominiumService{

	@Autowired
	private CondominiumDao condominiumDao;
	
	@Autowired
	private UserService userService;

	@Autowired
	private EntityManager em;

	@Override
	public void save(Condominium condominium) {
		if(condominium.getIdCondominium() == null) {
			
			condominiumDao.save(condominium);
			
			User trustee = userService.logged();
			trustee.setCondominium(condominium);
			userService.update(trustee);		
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Condominium read(Long id) {
		return null;
	}

	@Override
	public List<Condominium> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Condominium> listPage(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	public Condominium loadById(Long id) {
		Optional<Condominium> optional = condominiumDao.findById(id);
		return optional.orElse(null);
	}

	@Override
	public void update(Condominium condominium) {
		condominiumDao.save(condominium);
	}

	@Override
	public void delete(Condominium condominium) {
		condominiumDao.delete(condominium);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validate(Condominium condominium, BindingResult validation) {
		if (condominium.getIdCondominium() == null) {
			if (condominium.getCnpj() != null && condominiumDao.existsByCnpj(condominium.getCnpj())) {
				validation.rejectValue("cnpj", "Unique");
			}
		}
		else {
			if(condominium.getCnpj() != null && condominiumDao.existsByCnpjAndIdCondominiumNot(condominium.getCnpj(), condominium.getIdCondominium())) {
				validation.rejectValue("cnpj", "Unique");
			}			
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public Condominium read() {
//		Condominium condominium = userService.logged().getCondominium();
//		if(condominium == null || condominium.getIdCondominium() == null) {
//			throw new RuntimeException("Não foi possível recuperar o condimínio.");
//		}
//		em.detach(condominium);
//		return this.loadById(condominium.getIdCondominium());
		return userService.logged().getCondominium();
	}

}
