package com.my.restboard.application.User;

import com.my.restboard.common.CommonResponse;
import com.my.restboard.common.Error;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@PostMapping("/auth/signup")
	public CommonResponse<?> createUser(@RequestBody UserRequestDTO request) {

		try {

			UserEntity user = UserEntity.builder()
							.userId(request.getUserId())
							.nickName(request.getNickName())
							.password(passwordEncoder.encode(request.getPassword()))
							.build();

			userService.create(user);

			CommonResponse response = CommonResponse.builder()
									.success(true)
									.build();
			return response;

		}catch (Exception e) {

			Error error = Error.builder()
					.message(e.getMessage())
					.status(500)
					.build();

			CommonResponse response = CommonResponse.builder()
					.success(false)
					.error(error)
					.build();

			return response;
		}
	}
}
