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
@Schema(description = "유저 회원가입")
public class UserSavedRequestDTO {

	@Schema(description = "아이디", nullable = false, example = "admin")
	private String userId;
	@Schema(description = "닉네임", nullable = false, example = "관리자")
	private String nickName;
	@Schema(description = "비밀번호", nullable = false, example = "admin")
	private String password;
}
