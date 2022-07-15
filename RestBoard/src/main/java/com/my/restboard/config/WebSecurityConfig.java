package com.my.restboard.config;

import com.my.restboard.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationFilter jwtAuthenticationFilter;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.cors()
				.and()
				.csrf()
						.disable()
				.httpBasic()
						.disable()
				.sessionManagement()
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.authorizeRequests()
						.antMatchers("/", "/auth/**", "/v3/api-docs/**",
								"/swagger-ui/**").permitAll()
				.anyRequest()
						.authenticated();

		http.addFilterAfter(		// filter 등록.
						jwtAuthenticationFilter,
						CorsFilter.class
		);
	}
}