package com.andreisoimu.cine_mille.service.impl;


import com.andreisoimu.cine_mille.model.dao.User;
import com.andreisoimu.cine_mille.repository.jpa.UserJpaRepository;
import com.andreisoimu.cine_mille.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	public UserServiceImpl(UserJpaRepository userRepository) {
		this.userRepository = userRepository;
	}
	private final UserJpaRepository userRepository;
	
	@Override
	public User loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<User> user = userRepository.loadUserByUsername(username);
		
		user.orElseThrow(() -> new UsernameNotFoundException(String.format("User not found: %s", username)));
		
		return user.orElse(null);
	}
	
	public void insert(User user) throws Exception {
		if(user != null && user.getUsername() != null && user.getPassword() != null) {
			userRepository.save(user);
		} else
			throw new Exception("Username and password are required!");
		
	}

	@Transactional
	public void update(Long id, User userView) {
		Optional<User> user = userRepository.findById(id);
		
		user.orElseThrow(() -> new UsernameNotFoundException(String.format("User with id %d not present!", id)));
		
		if(userView.getFirstName() != null)
			user.get().setFirstName(userView.getFirstName());

		userRepository.save(user.get());
	}
	
	@Transactional
    public void upsert(User user) throws Exception {
        Optional<User> optionalUser = userRepository.loadUserByUsername(user.getUsername());

        if (optionalUser.isEmpty()) {
            insert(user);
        } else {
            update(optionalUser.get().getId(), user);
        }
    }
}