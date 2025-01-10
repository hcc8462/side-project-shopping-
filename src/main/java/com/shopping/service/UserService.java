package com.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.shopping.dao.UserRepository;
import com.shopping.model.entity.User;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User createUser(String username, String password, String email) {
		User user = new User();
		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);
		return userRepository.save(user);
	}

	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}
}
