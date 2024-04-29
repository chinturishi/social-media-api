package com.rishi.config;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.security.core.Authentication;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


public class JwtProvider {

	private SecretKey key = Keys.hmacShaKeyFor(JwtConstant.SECRET_KEY.getBytes());
	
	public String generateToken(Authentication authentication) {
		
		String jwt = Jwts.builder()
				.setIssuer("Rishi")
				.setIssuedAt(new Date())
				.setExpiration(new Date(new Date().getTime()+86400000))
				.claim("email", authentication.getName())
				.signWith(key)
				.compact();
		return jwt;
	}
	
	public String getEmailFromJwtToken(String jwt) {
		jwt=jwt.substring(7);
		Claims claims=Jwts.parser()
				.setSigningKey(key).build()
				.parseClaimsJws(jwt).getBody();
		String email=String.valueOf(claims.get("email"));
		return email;
	}
}
