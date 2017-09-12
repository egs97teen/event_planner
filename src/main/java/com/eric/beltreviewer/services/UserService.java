package com.eric.beltreviewer.services;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.eric.beltreviewer.models.User;
import com.eric.beltreviewer.repositories.RoleRepository;
import com.eric.beltreviewer.repositories.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private BCryptPasswordEncoder bCryptPasswordEncoder;
	private RoleRepository roleRepository;
	
	public UserService(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
		this.userRepository = userRepository;
		this.bCryptPasswordEncoder = bCryptPasswordEncoder;
		this.roleRepository = roleRepository;
	}
	
	public void saveWithUserRole(User user) {
		user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
		user.setRoles(roleRepository.findByName("ROLE_USER"));
		userRepository.save(user);
	}
	
	public User findByEmail(String email) {
		return userRepository.findByEmail(email);
	}
	
	public User findById(Long id) {
		return userRepository.findOne(id);
	}
}
