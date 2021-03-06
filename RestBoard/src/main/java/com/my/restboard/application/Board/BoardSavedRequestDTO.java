package com.my.restboard.application.Board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@NoArgsConstructor
public class BoardSavedRequestDTO {

	@Schema(description = "제목", nullable = false, example = "제목")
	private String title;
	@Schema(description = "아이디", nullable = false, example = "admin")
	private String userId;
	@Schema(description = "내용", nullable = false, example = "내용")
	private String content;

	@Builder
	public BoardSavedRequestDTO(String title, String content, String userId) {
		this.title = title;
		this.content = content;
		this.userId = userId;
	}

	public BoardEntity toEntity() {
		return BoardEntity.builder()
				.title(title)
				.content(content)
				.userId(userId)
				.build();
	}
}

