package com.andreisoimu.cine_mille.repository.jpa;

import com.andreisoimu.cine_mille.model.dao.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserJpaRepository extends JpaRepository<User, Long>{

	@Query("SELECT u FROM User u WHERE u.username = ?1")
	Optional<User> loadUserByUsername(String username);
	
}
