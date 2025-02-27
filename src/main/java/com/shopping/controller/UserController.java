package com.shopping.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.model.dto.AuthResponse;
import com.shopping.model.entity.User;
import com.shopping.service.UserService;

@Controller
@RequestMapping("/register")
public class UserController {
	@GetMapping
	public String showRegisterPage() {
		return "register"; // 返回 register.html 頁面
	}

	@Autowired
	private UserService userService;

	@PostMapping
	public ResponseEntity<?> register(@RequestBody Map<String, String> registerRequest) {
		String username = registerRequest.get("username");
		String password = registerRequest.get("password");
		String email = registerRequest.get("email");
		String phoneNumberString = registerRequest.get("phoneNumber");
		Integer phoneNumber = null;
		try {
			phoneNumber = Integer.parseInt(phoneNumberString); // 字符串轉 Integer
		} catch (NumberFormatException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid phone number format");
		}
		// 檢查用戶名是否已存在
		if (userService.isUsernameTaken(username)) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is already taken");
		}

		// 創建新用戶
		User newUser = userService.createUser(username, password, email, phoneNumber);

		return ResponseEntity.ok(new HashMap<String, String>() {
			{
				put("message", "Registration successful");
			}
		});
	}

}
