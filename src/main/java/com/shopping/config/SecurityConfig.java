package com.shopping.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

import com.shopping.utils.JwtUtil;

@Configuration
public class SecurityConfig {
	
	private final JwtUtil jwtUtil;

	// 注入 JwtUtil 以便使用其驗證和生成 JWT
	public SecurityConfig(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, HandlerMappingIntrospector introspector)
            throws Exception {
        http.addFilterBefore(new JwtAuthenticationFilter(jwtUtil), UsernamePasswordAuthenticationFilter.class) // 添加 JwtAuthenticationFilter
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(new MvcRequestMatcher(introspector, "/login")).permitAll()  // 登錄頁面允許訪問
                .requestMatchers(new MvcRequestMatcher(introspector, "/register")).permitAll() // 註冊頁面允許訪問
                .requestMatchers(new MvcRequestMatcher(introspector, "/css/**")).permitAll()  // 靜態資源允許訪問
                .requestMatchers(new MvcRequestMatcher(introspector, "/js/**")).permitAll()
                .requestMatchers(new MvcRequestMatcher(introspector, "/images/**")).permitAll()
                .anyRequest().authenticated()  // 其他頁面需要認證
            )
            .logout(logout -> logout
                .logoutUrl("/logout") // 登出路徑
                .logoutSuccessUrl("/login") // 登出後跳轉
            )
            .sessionManagement(session -> session
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 無狀態 session
            );

        return http.build();
    }

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder(); // 密碼加密
	}
}
