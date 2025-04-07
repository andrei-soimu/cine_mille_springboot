package com.andreisoimu.cine_mille.service;

import com.andreisoimu.cine_mille.model.dao.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public interface UserService extends UserDetailsService {

	/**
	 * @param username the username identifying the user whose data is required.
	 * @return User
	 * @throws UsernameNotFoundException
	 */
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException;

	/**
	 * Insert or update user
	 * @param user
	 */
    public void upsert(User user) throws Exception;

}
