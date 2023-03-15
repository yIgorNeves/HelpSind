package com.ufop.HelpSind.serviceImpl;

import com.ufop.HelpSind.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import com.ufop.HelpSind.dao.UserDao;
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private UserDao dao;


	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = dao.findByUsername(username);

		if(user == null){
			throw new RuntimeException("Usuário não encontrado!");
		}

		Set<GrantedAuthority> authorities = new HashSet<>();

		user.getAuth().stream().forEach(each ->{
			authorities.add(new SimpleGrantedAuthority(each.name()));
		});

		org.springframework.security.core.userdetails.User userSpring = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);

		return userSpring;
	}
}
