package com.my.restboard.application.User;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "유저 로그인")
public class UserSigninRequestDTO {

	@Schema(description = "아이디", nullable = false, example = "admin")
	private String userId;
	@Schema(description = "비밀번호", nullable = false, example = "admin")
	private String password;
}
