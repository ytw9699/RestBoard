package com.my.restboard.application.User;

import com.my.restboard.common.CommonResponse;
import com.my.restboard.common.Error;
import com.my.restboard.security.TokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;
	private final TokenProvider tokenProvider;
	private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@GetMapping("/test")
	public String test(){

		return "test";
	}

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

	@PostMapping("/auth/signin")
	public CommonResponse<?> authenticate(@RequestBody UserRequestDTO request) {

		try{

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

		}catch(Exception e) {

			String errormsg;

			if(e instanceof BadCredentialsException){//비밀번호가 틀린경우

				errormsg = "아이디가 없거나 비밀번호가 틀립니다.";//보안상 똑같은 결과를 보여줘야한다.

			}else if(e instanceof UsernameNotFoundException){//아이디가 없는 경우

				errormsg = "아이디가 없거나 비밀번호가 틀립니다.";//보안상 똑같은 결과를 보여줘야한다.

			}else {
				errormsg = "로그인 할 수 없습니다. 관리자에게 문의해주세요";
			}

			Error error = Error.builder()
					.message(errormsg)
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
