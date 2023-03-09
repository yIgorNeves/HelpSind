package com.ufop.HelpSind.service;

import com.ufop.HelpSind.domain.User;

public interface UserService extends CrudService<User, Long>{

	public void saveTrustee (User user);
	
	public void saveTenant (User user);
	
	public void saveAdmin(User user);
	
	public User read(String username);
	
	public User logged();
	
	

}
