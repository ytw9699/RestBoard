package com.my.restboard.security;

import com.my.restboard.application.User.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

@Slf4j
@Service
public class TokenProvider {

	private static final String SECRET_KEY = "123gf1ffdhtycUeR4uUAt7WJa5raD7EN3Os4yyYuHxMEbSFdd4XXyffgB0F7Bq4dH";

	public String createToken(UserEntity userEntity) {

		Date expiryDate = Date.from(
				Instant.now()
						.plus(1, ChronoUnit.HOURS));//1시간후

		return Jwts.builder()
						.signWith(SignatureAlgorithm.HS256, SECRET_KEY)
						.setSubject(userEntity.getUserId())
						.setIssuer("rest board")
						.setIssuedAt(new Date())
						.setExpiration(expiryDate)
						.compact();
	}

	public String validateAndGetUserId(String token) {
		Claims claims = Jwts.parser()
						.setSigningKey(SECRET_KEY)
						.parseClaimsJws(token)
						.getBody();

		return claims.getSubject();
	}
}
