package com.my.restboard.application.User;

import com.my.restboard.common.CommonResponse;
import com.my.restboard.security.TokenProvider;
import io.swagger.v3.oas.annotations.Operation;
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
	private final TokenProvider tokenProvider;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@Operation(summary = "회원가입", description = "아이디, 닉네임, 비밀번호를 받아 회원가입합니다.")
	@PostMapping("/auth/signup")
	public CommonResponse<?> createUser(@RequestBody UserSavedRequestDTO request){

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
	}

	@Operation(summary = "로그인", description = "아이디, 비밀번호를 받아 로그인합니다.")
	@PostMapping("/auth/signin")
	public CommonResponse<?> authenticate(@RequestBody UserSigninRequestDTO request) {

			UserEntity userEntity = userService.getUserAuthenticated(
					request.getUserId(),
					request.getPassword(),
					passwordEncoder
			);

			final String token = "Bearer "+tokenProvider.createToken(userEntity); //토큰 생성

			CommonResponse response = CommonResponse.builder()
									.success(true)
									.data(token)
									.build();

			return response;
	}
}
