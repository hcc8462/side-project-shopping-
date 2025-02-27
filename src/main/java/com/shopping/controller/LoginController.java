package com.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shopping.model.dto.AuthRequest;
import com.shopping.model.dto.AuthResponse;
import com.shopping.model.entity.User;
import com.shopping.service.UserService;
import com.shopping.utils.JwtUtil;

@Controller
@RequestMapping("/login")
public class LoginController {

	@Autowired
	private UserService userService;

	@Autowired
	private JwtUtil jwtUtil;

	// 顯示登入頁面，處理 GET 請求
	@GetMapping
	public String showLoginPage() {
		return "login"; // 返回 login.html 頁面
	}

	// 處理登入請求，處理 POST 請求
	@PostMapping
	public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
		// 查詢資料庫中的用戶
		User user = userService.findByUsername(authRequest.getUsername());

		// 檢查用戶是否存在且密碼正確
		if (user != null && userService.checkPassword(authRequest.getPassword(), user.getPassword())) {
			// 密碼驗證成功，生成 JWT
			String token = jwtUtil.generateToken(authRequest.getUsername());
			return ResponseEntity.ok(new AuthResponse(token)); // 返回 JWT
		} else {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
		}
	}
}
