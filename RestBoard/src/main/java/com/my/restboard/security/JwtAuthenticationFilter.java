package com.my.restboard.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.my.restboard.common.CommonResponse;
import com.my.restboard.common.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	@Autowired
	private TokenProvider tokenProvider;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

		try {

			String token = parseBearerToken(request);

			if (token != null && !token.equalsIgnoreCase("null")) {

				String userId = tokenProvider.validateAndGetUserId(token);
				AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
								userId,
								null,
								AuthorityUtils.NO_AUTHORITIES
				);

				authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
				securityContext.setAuthentication(authentication);
				SecurityContextHolder.setContext(securityContext);//SecurityContext에 등록

			}

			filterChain.doFilter(request, response);

		} catch (Exception ex) {

			ex.printStackTrace();

			Error error = Error.builder()
					.message("인증되지 않았습니다.")
					.status(500)
					.build();

			CommonResponse commonResponse =  CommonResponse.builder()
					.success(false)
					.error(error)
					.build();

			ObjectMapper objectMapper = new ObjectMapper();

			response.getWriter().write(objectMapper.writeValueAsString(commonResponse));
		}
	}

	private String parseBearerToken(HttpServletRequest request) {

		String bearerToken = request.getHeader("Authorization");

		if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
			return bearerToken.substring(7);
		}

		return null;
	}
}
