package com.devsuperior.movieflix.services;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.movieflix.dto.UserDTO;
import com.devsuperior.movieflix.entities.User;
import com.devsuperior.movieflix.repositories.UserRepository;
import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@Service
public class UserService implements UserDetailsService{

	private static Logger logger = LoggerFactory.getLogger(UserService.class);

	@Autowired
	private AuthService authService;
	
	@Autowired
	private UserRepository repository;
	@Transactional(readOnly = true)
	public UserDTO findById(Long id) {
		authService.validateSelfOrAdmin(id);
		Optional<User> obj = repository.findById(id);
//		logger.info("Passei no UserService ==> findById. Id:  " + id);		
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new UserDTO(entity);
	}
	@Transactional(readOnly = true)
	public UserDTO findByEmail(String email) {
		Optional<User> obj = Optional.of(repository.findByEmail(email));
		User myuser = repository.findByEmail(email);
//		logger.info("Passei no UserService ==> findByEmail. Id:  " + email);
		logger.info("Passei no UserService.java e chamei > authService.validateSelfOrAdmin  " + email);

		authService.validateSelfOrAdmin( myuser.getId());
//		logger.info("Passei no UserService ==> findByEmail. Id:  " + myuser.getId());
		User entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found"));
		return new UserDTO(entity);
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		User user = repository.findByEmail(username);
		if (user == null) {
			logger.error(" UserService.java ==> loadUserByUsername > User found: " + username);
			throw new UsernameNotFoundException("Email not found");
		}
		logger.info(" UserService.java ==> loadUserByUsername > User found: " + username);
		return user;
	}
	
}
