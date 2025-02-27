package com.shopping.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

//	public User createUser(String username, String password, String email, Integer phonenumber) {
//		User user = new User();
//		user.setUsername(username);
//		user.setPassword(password);
//		user.setEmail(email);
//		user.setPhoneNumber(phonenumber);
//		return userRepository.save(user);
//	}

	public boolean checkPassword(String inputPassword, String storedPassword) {
		return passwordEncoder.matches(inputPassword, storedPassword); // 比對密碼
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username); // 查詢用戶
	}

	public boolean isUsernameTaken(String username) {
		return userRepository.findByUsername(username) != null;
	}

	public User createUser(String username, String password, String email, Integer phoneNumber) {
		// 密碼加密
		String encodedPassword = new BCryptPasswordEncoder().encode(password);

		// 使用傳來的 phoneNumber 和其他資料創建 User
		User user = new User(username, encodedPassword, email, phoneNumber);
		return userRepository.save(user);
	}
}
