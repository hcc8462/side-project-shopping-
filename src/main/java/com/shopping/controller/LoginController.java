package com.shopping.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/login")
public class LoginController {
	
	@GetMapping
	public String showLoginPage() {
		return "login"; 
	}

	@PostMapping
	public String processLogin(@RequestParam("username") String username, @RequestParam("password") String password,
			Model model) {
		// 處理登入邏輯（假設驗證成功）
		if ("admin".equals(username) && "password".equals(password)) {
			model.addAttribute("message", "登入成功！");
			return "redirect:/dashboard"; // 重定向到主頁面
		} else {
			model.addAttribute("error", "帳號或密碼錯誤！");
			return "login";
		}
	}
}
