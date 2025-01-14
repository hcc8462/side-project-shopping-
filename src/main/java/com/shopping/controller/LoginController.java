package com.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.shopping.model.dto.AuthRequest;
import com.shopping.model.dto.AuthResponse;
import com.shopping.utils.JwtUtil;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
    private JwtUtil jwtUtil;
	
	@GetMapping
	public String showLoginPage() {
		return "login";
	}
	

	 // 處理登入請求，接收 JSON 格式的資料
    @PostMapping
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            // 驗證用戶名和密碼
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));

            // 如果成功，生成 JWT
            String token = jwtUtil.generateToken(authRequest.getUsername());

            // 返回 JWT 作為登錄成功的響應
            return ResponseEntity.ok(new AuthResponse(token));

        } catch (AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }
}
