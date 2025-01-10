package com.shopping.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Date;
import javax.crypto.spec.SecretKeySpec;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {
	private String secretKey = "secret"; // 使用一個私密的密鑰

	// 生成 JWT
	public String generateToken(String username) {
	    SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");  // 創建 SecretKey

	    return Jwts.builder()
	            .setSubject(username)
	            .setIssuedAt(new Date())
	            .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60)) // 1 小時有效
	            .signWith(key, SignatureAlgorithm.HS256)  // 使用 SecretKeySpec 和 HMACSHA256
	            .compact();
	}

	// 驗證 JWT
	public Claims extractClaims(String token) {
	    SecretKeySpec key = new SecretKeySpec(secretKey.getBytes(), "HmacSHA256");  // 創建 SecretKey

	    return Jwts.parserBuilder()  
	            .setSigningKey(key)  
	            .build()  // 生成解析器
	            .parseClaimsJws(token)  // 解析 JWT token
	            .getBody();  // 獲取 Claims
	}

	// 獲取用戶名
	public String extractUsername(String token) {
		return extractClaims(token).getSubject();
	}

	// 檢查 token 是否過期
	public boolean isTokenExpired(String token) {
		return extractClaims(token).getExpiration().before(new Date());
	}

	// 驗證 token 是否有效
	public boolean validateToken(String token) {
		   return !isTokenExpired(token); // 只需要檢查是否過期即可
	}
	
	public Authentication getAuthentication(String token) {
	    String username = extractUsername(token); // 從 token 中提取用戶名
	    return new UsernamePasswordAuthenticationToken(username, null, new ArrayList<>()); // 這裡的 null 可以換成用戶的權限或角色
	}
}
