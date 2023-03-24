package com.ufop.HelpSind.serviceImpl;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import com.ufop.HelpSind.dao.UserDao;
import com.ufop.HelpSind.domain.User;
import com.ufop.HelpSind.enums.Authorization;
import com.ufop.HelpSind.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	public void save(User user) {
		if(user.getId() == null) {
			System.out.println(user.getPassword());
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			userDao.save(user);
		}
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User read(Long id) {
		return userDao.findById(id).get();
	}
	
	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public User read(String username) {
		return userDao.findByUsername(username);
	}

	@Override
	public List<User> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<User> listPage(Pageable page) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update(User user) {
		if(!user.getPassword().startsWith("{bcrypt}")) {
			user.setPassword(passwordEncoder.encode(user.getPassword()));
		}
		if(user.getAuth().isEmpty()) {
			user.setAuth(read(user.getId()).getAuth());
		}
		userDao.save(user);		
	}

	@Override
	public void delete(User user) {
		userDao.delete(user);
	}

	@Override
	public void saveTrustee(User user) {
		user.getAuth().add(Authorization.TRUSTEE);
		save(user);
	}

	@Override
	public void saveTenant(User user) {
		user.getAuth().add(Authorization.TENANT);
		save(user);
	}

	@Override
	public void saveAdmin(User user) {
		user.getAuth().add(Authorization.ADMIN);
		save(user);
	}

	@Override
	@Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
	public void validate(User user, BindingResult validation) {
		if(user.getId() == null) {
			if(user.getUsername() != null && userDao.existsByUsername(user.getUsername())) {
				validation.rejectValue("username", "Unique");
			}
		}
		
		else {
			if(user.getUsername() != null && userDao.existsByUsernameAndIdNot(user.getUsername(), user.getId())) {
				validation.rejectValue("username", "Unique");
			}
		}
		if(!user.getActive()) {
			validation.rejectValue("active", "AssertTrue");
		}
		
	}

	@Override
	public User logged() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth == null || auth.getAuthorities().contains(new SimpleGrantedAuthority("ROLE_ANONYMOUS"))) {
			return null;
		}
		return userDao.findOneByUsername(auth.getName());
	}

}
